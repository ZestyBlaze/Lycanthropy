package net.zestyblaze.lycanthropy.common.utils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.zestyblaze.lycanthropy.Lycanthropy;

public class LycanthropyUtils {
    /**
     * Streamline registration process with a standard settings
     * @return FabricItemSettings with Lycanthropy Item group
     */
    public static FabricItemSettings gen() {
        return new FabricItemSettings().group(Lycanthropy.LYCANTHROPY_GROUP);
    }
}
