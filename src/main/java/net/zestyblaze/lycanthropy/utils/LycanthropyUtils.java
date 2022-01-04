package net.zestyblaze.lycanthropy.utils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.zestyblaze.lycanthropy.Lycanthropy;

public class LycanthropyUtils {
    public static FabricItemSettings gen() {
        return new FabricItemSettings().group(Lycanthropy.LYCANTHROPY_GROUP);
    }
}
