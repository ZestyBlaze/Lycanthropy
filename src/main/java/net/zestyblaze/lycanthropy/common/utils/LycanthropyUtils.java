package net.zestyblaze.lycanthropy.common.utils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vector4f;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyEntityTypeInit;

import java.util.concurrent.atomic.AtomicBoolean;

public class LycanthropyUtils {
    private static WerewolfEntity entity;
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

    public static WerewolfEntity getWerewolf(PlayerEntity player) {
        if (LycanthropyUtils.isWerewolf(player)) {
            if(entity == null) {
                entity = LycanthropyEntityTypeInit.WEREWOLF.create(player.world);
                assert entity != null;
            }
            return entity;
        }
        return null;
    }

    /**
     * Converts int to rgba value. For example biomecolors to rgb
     * @param i biomecolor/foliage color
     * @return vec4 containing rgba
     */
    public static Vector4f intToRGB(int i){
        float r = ((i >> 16) & 0xff) / 255.0f;
        float g = ((i >>  8) & 0xff) / 255.0f;
        float b = ((i      ) & 0xff) / 255.0f;
        float a = ((i >> 24) & 0xff) / 255.0f;

        return new Vector4f(r,g,b,a);
    }


}