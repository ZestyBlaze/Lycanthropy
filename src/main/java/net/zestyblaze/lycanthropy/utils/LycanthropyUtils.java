package net.zestyblaze.lycanthropy.utils;

import net.minecraft.item.Item;
import net.zestyblaze.lycanthropy.Lycanthropy;

public class LycanthropyUtils {

    public static Item.Settings gen() {
        return new Item.Settings().group(Lycanthropy.LYCANTHROPY_GROUP);
    }


}
