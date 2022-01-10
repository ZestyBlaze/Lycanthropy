package net.zestyblaze.lycanthropy.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;

public class LycanthropyPlayerHungerComponent implements AutoSyncedComponent, ServerTickingComponent {
    private final PlayerEntity player;
    private static final int maxHunger = 200;
    private int hunger = maxHunger;
    private int foodTickTimer = 0;
    public LycanthropyPlayerHungerComponent(PlayerEntity player) {
        this.player = player;
    }

    public int getMaxHunger(){
        return maxHunger;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void increaseHunger(int increaseAmount){
        for(int buffer = increaseAmount; getHunger() < getMaxHunger() && buffer > 0; buffer--){
            setHunger(getHunger() + 1);
            LycanthropyComponentInit.WEREWOLF_HUNGER.sync(player);
        }
    }

    public void decreaseHunger(int decreaseAmount){
        for(int buffer = decreaseAmount; getHunger() > 0 && buffer > 0; buffer--){
            setHunger(getHunger() - 1);
            LycanthropyComponentInit.WEREWOLF_HUNGER.sync(player);
        }
    }

    @Override
    public void serverTick() {
        //updateHunger(); TODO: implement
        LycanthropyComponentInit.WEREWOLF.maybeGet(player).ifPresent(lycanthropyPlayerComponent -> {
            if(player.isSneaking()){
                decreaseHunger(2);
            }else{
                increaseHunger(2);
            }
        });
    }

    /**
     * vanilla copy but with gutted exhaustion and saturation
     */
    public void updateHunger(){
        Difficulty difficulty = player.world.getDifficulty();
        boolean bl = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        if (bl && getHunger() >= 180) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 10) {
                player.heal(1.0F);
                decreaseHunger(1);
                this.foodTickTimer = 0;
            }
        } else if (getHunger() <= 0) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.damage(DamageSource.STARVE, 1.0F);
                }
                this.foodTickTimer = 0;
            }
        } else {

            this.foodTickTimer++;
            if(foodTickTimer >= 10){
                decreaseHunger(1);
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("Hunger")) {
            setHunger(tag.getInt("Hunger"));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("Hunger", getHunger());
    }
}
