package net.zestyblaze.lycanthropy.common.utils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;

import java.util.concurrent.atomic.AtomicBoolean;

public class LycanthropyUtils {
    /**
     * Streamline registration process with a standard settings
     * @return FabricItemSettings with Lycanthropy Item group
     */
    public static FabricItemSettings gen() {
        return new FabricItemSettings().group(Lycanthropy.LYCANTHROPY_GROUP);
    }

    /**
     * Checks if the player currently is in werewolf form
     * @param player
     * @return true of false depending on if the player is in werewolf form
     */
    public static boolean isWerewolf(PlayerEntity player) {
        AtomicBoolean isWerewolf = new AtomicBoolean(false);
        LycanthropyComponentInit.WEREWOLF.maybeGet(player).ifPresent(lycanthropyPlayerComponent -> {
            isWerewolf.set(lycanthropyPlayerComponent.getIsWerewolf());
        });
        return isWerewolf.get();
    }


}