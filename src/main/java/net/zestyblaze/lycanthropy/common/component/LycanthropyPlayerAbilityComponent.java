package net.zestyblaze.lycanthropy.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class LycanthropyPlayerAbilityComponent implements AutoSyncedComponent, ServerTickingComponent {
    private final PlayerEntity player;
    //Some potential abilities
    private int bloodlustLevel = 0;
    private int ironGutLevel = 0;
    private int beastSenseLevel = 0;
    private int thoughSkinLevel = 0;
    private int movementSpeedLevel = 0;
    private int strengthLevel = 0;

    public LycanthropyPlayerAbilityComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag) {
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {
    }
}
