package net.zestyblaze.lycanthropy.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;

public class LycanthropyPlayerRageComponent implements AutoSyncedComponent, ServerTickingComponent {
    private final PlayerEntity player;
    private static final int maxRage = 10;
    private int rage = maxRage;
    public LycanthropyPlayerRageComponent(PlayerEntity player) {
        this.player = player;
    }

    public int getMaxRage(){
        return maxRage;
    }

    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }

    public void increaseRage(int increaseAmount){
        for(int buffer = increaseAmount; getRage() < getMaxRage() && buffer > 0; buffer--){
            setRage(getRage() + 1);
            LycanthropyComponentInit.WEREWOLF_RAGE.sync(player);
        }
    }

    public void decreaseRage(int decreaseAmount){
        for(int buffer = decreaseAmount; getRage() > 0 && buffer > 0; buffer--){
            setRage(getRage() - 1);
            LycanthropyComponentInit.WEREWOLF_RAGE.sync(player);
        }
    }

    @Override
    public void serverTick() {
        //updateRage(); TODO: implement
        LycanthropyComponentInit.WEREWOLF.maybeGet(player).ifPresent(lycanthropyPlayerComponent -> {
            if(player.isSneaking()){
                decreaseRage(1);
            }else{
                increaseRage(1);
            }
        });
    }


    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("Rage")) {
            setRage(tag.getInt("Rage"));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("Rage", getRage());
    }
}
