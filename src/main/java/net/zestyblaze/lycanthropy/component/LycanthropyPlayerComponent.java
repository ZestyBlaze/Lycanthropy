package net.zestyblaze.lycanthropy.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.registry.LycanthropyComponentInit;

public class LycanthropyPlayerComponent implements AutoSyncedComponent, ServerTickingComponent, ILycanthropy {
    private final PlayerEntity player;
    private WerewolfEntity werewolfEntity = null;
    public boolean isWerewolf = false;
    public boolean hasWerewolfForm = false;
    public int werewolfLevel = 0;
    public int werewolfMaxLevel = 10; //TODO

    public LycanthropyPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {

    }

    @Override
    public WerewolfEntity hasWerewolfEntity(){
        return werewolfEntity;
    }

    @Override
    public void setWerewolfEntity(WerewolfEntity werewolf){
        this.werewolfEntity = werewolf;
        LycanthropyComponentInit.WEREWOLF.sync(player);
    }

    @Override
    public void setWerewolf(boolean hasWerewolfForm) {
        this.hasWerewolfForm = hasWerewolfForm;
        LycanthropyComponentInit.WEREWOLF.sync(player);
    }

    @Override
    public void activateWerewolfForm(boolean active){
        this.isWerewolf = active;
        LycanthropyComponentInit.WEREWOLF.sync(player);
    }

    @Override
    public void setWerewolfLevel(int level) {
        this.werewolfLevel = level;
    }

    @Override
    public int getWerewolfLevel() {
        return this.werewolfLevel;
    }

    @Override
    public void increaseWerewolfLevel() {
        if(getWerewolfLevel() < werewolfMaxLevel){
            this.werewolfLevel += 1;
            LycanthropyComponentInit.WEREWOLF.sync(player);
        }
    }

    @Override
    public void decreaseWerewolfLevel() {
        if(getWerewolfLevel() > 0){
            this.werewolfLevel -= 1;
            LycanthropyComponentInit.WEREWOLF.sync(player);
        }
    }

    @Override
    public boolean hasWerewolfForm() {
        return this.hasWerewolfForm;
    }

    @Override
    public boolean isWerewolf() {
        return this.hasWerewolfForm && this.isWerewolf;
    }


    @Override
    public void readFromNbt(NbtCompound tag) {
        setWerewolf(tag.getBoolean("Werewolf"));
        tag.getInt("WerewolfLevel");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("Werewolf", isWerewolf);
        tag.putInt("WerewolfLevel", werewolfLevel);
    }
}
