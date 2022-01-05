package net.zestyblaze.lycanthropy.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.WorldAccess;
import net.zestyblaze.lycanthropy.api.event.TransformationEvents;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;

public class LycanthropyPlayerComponent implements AutoSyncedComponent, ServerTickingComponent, ILycanthropy {
    private final PlayerEntity player;
    private WerewolfEntity werewolfEntity = null;
    private boolean isWerewolf = false;
    private boolean canBecomeWerewolf = false;
    private int werewolfLevel = 0;
    private final int werewolfMaxLevel = 10; //TODO, make config or adjust the value to something appropriate

    public LycanthropyPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
        WorldAccess worldAccess = player.world;
        if(canBecomeWerewolf){
            if(isWerewolf && worldAccess.getDimension().getMoonPhase(worldAccess.getLunarTime()) != 0){
                tryActivateWerewolfForm(false, false);
            }else if(!isWerewolf && worldAccess.getDimension().getMoonPhase(worldAccess.getLunarTime()) == 0){
                tryActivateWerewolfForm(true, false);
            }
        }
    }

    @Override
    public WerewolfEntity getRenderedWerewolfEntity(){
        return werewolfEntity;
    }

    @Override
    public void setRenderedWerewolfEntity(WerewolfEntity werewolf){
        this.werewolfEntity = werewolf;
        LycanthropyComponentInit.WEREWOLF.sync(player);
    }

    @Override
    public void setCanBecomeWerewolf(boolean canBecomeWerewolf) {
        if(!TransformationEvents.CANCEL_TRANSFORMATION_EVENT.invoker().shouldCancel(player)){
            this.canBecomeWerewolf = canBecomeWerewolf;
            LycanthropyComponentInit.WEREWOLF.sync(player);
            TransformationEvents.ON_CAN_TRANSFORM_EVENT.invoker().onCanTransform(player);
        }
    }

    @Override
    public void tryActivateWerewolfForm(boolean active, boolean force){
        if(!TransformationEvents.CANCEL_TRANSFORMATION_EVENT.invoker().shouldCancel(player)){
            if(force && active){
                setCanBecomeWerewolf(true);
                this.isWerewolf = true;
            }else if(!force){
                if(canBecomeWerewolf){
                    this.isWerewolf = active;
                }
            }else{
                this.isWerewolf = false;
                setCanBecomeWerewolf(false);
            }
            LycanthropyComponentInit.WEREWOLF.sync(player);
            TransformationEvents.ON_TRANSFORMATION_EVENT.invoker().onTransformation(player);
        }
    }

    private void setWerewolfLevel(int level) {
        this.werewolfLevel = level;
    }

    @Override
    public int getWerewolfLevel() {
        return this.werewolfLevel;
    }

    @Override
    public void increaseWerewolfLevel() {
        if(getWerewolfLevel() < werewolfMaxLevel){
            setWerewolfLevel(getWerewolfLevel() + 1);
            LycanthropyComponentInit.WEREWOLF.sync(player);
        }
    }

    @Override
    public void decreaseWerewolfLevel() {
        if(getWerewolfLevel() > 0){
            setWerewolfLevel(getWerewolfLevel() - 1);
            LycanthropyComponentInit.WEREWOLF.sync(player);
        }
    }

    @Override
    public boolean canBecomeWerewolf() {
        return this.canBecomeWerewolf;
    }

    @Override
    public boolean isWerewolf() {
        return this.canBecomeWerewolf && this.isWerewolf;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        setCanBecomeWerewolf(tag.getBoolean("CanBecomeWerewolf"));
        tag.getInt("WerewolfLevel");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("CanBecomeWerewolf", canBecomeWerewolf);
        tag.putInt("WerewolfLevel", werewolfLevel);
    }
}
