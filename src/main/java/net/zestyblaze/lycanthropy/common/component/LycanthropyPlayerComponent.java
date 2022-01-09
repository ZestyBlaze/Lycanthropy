package net.zestyblaze.lycanthropy.common.component;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.WorldAccess;
import net.zestyblaze.lycanthropy.api.event.TransformationEvents;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;

import java.util.UUID;

public class LycanthropyPlayerComponent implements AutoSyncedComponent, ServerTickingComponent, ILycanthropy {

    private static final EntityAttributeModifier WEREWOLF_MOVEMENT_SPEED_MODIFIER = new EntityAttributeModifier(UUID.fromString("718104a6-aa19-4b53-bad9-1f9edd46d38a"), "Transformation modifier", 0.16, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier WEREWOLF_STEP_HEIGHT_MODIFIER = new EntityAttributeModifier(UUID.fromString("af386c1c-b4fc-429d-97b6-b2559826fa9d"), "Transformation modifier", 0.4, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier WEREWOLF_REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("4c6d90ab-41ad-4d8a-b77a-7329361d3a7b"), "Transformation modifier", 1.4, EntityAttributeModifier.Operation.ADDITION);


    private final PlayerEntity player;
    private WerewolfEntity werewolfEntity = null;
    private boolean isWerewolf = false;
    private boolean canBecomeWerewolf = false;
    private int werewolfLevel = 0;
    private final int werewolfMaxLevel = 10; //TODO, make config or adjust the value to something appropriate

    public LycanthropyPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void serverTick() {
        WorldAccess worldAccess = player.world;
        EntityAttributeInstance movementSpeedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance stepHeight = player.getAttributeInstance(StepHeightEntityAttributeMain.STEP_HEIGHT);
        EntityAttributeInstance reach = player.getAttributeInstance(ReachEntityAttributes.REACH);
        if(canBecomeWerewolf){

            if(isWerewolf()){
                if(!stepHeight.hasModifier(WEREWOLF_STEP_HEIGHT_MODIFIER)){
                   stepHeight.addPersistentModifier(WEREWOLF_STEP_HEIGHT_MODIFIER);
                }
                if(!reach.hasModifier(WEREWOLF_REACH_MODIFIER)){
                    reach.addPersistentModifier(WEREWOLF_REACH_MODIFIER);
                }
                if(player.isSprinting() && !movementSpeedAttribute.hasModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER)){
                    movementSpeedAttribute.addPersistentModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER);
                }else if(!player.isSprinting() && movementSpeedAttribute.hasModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER)){
                    movementSpeedAttribute.removeModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER);
                }
            }
            if(isWerewolf() && worldAccess.getDimension().getMoonPhase(worldAccess.getLunarTime()) != 0){
                tryActivateWerewolfForm(false, false);
            }else if(!isWerewolf && worldAccess.getDimension().getMoonPhase(worldAccess.getLunarTime()) == 0){
                tryActivateWerewolfForm(true, false);
            }
        }
        if (!isWerewolf && (stepHeight.hasModifier(WEREWOLF_STEP_HEIGHT_MODIFIER) || reach.hasModifier(WEREWOLF_REACH_MODIFIER) || movementSpeedAttribute.hasModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER))){
            movementSpeedAttribute.removeModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER);
            stepHeight.removeModifier(WEREWOLF_STEP_HEIGHT_MODIFIER);
            reach.removeModifier(WEREWOLF_REACH_MODIFIER);
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
