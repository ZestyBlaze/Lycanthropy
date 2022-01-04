package net.zestyblaze.lycanthropy.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.item.DebugItem;
import net.zestyblaze.lycanthropy.item.GuideBookItem;

public class LycanthropyItemInit {
    public static final Item DEBUG_ITEM = new DebugItem(new FabricItemSettings().group(ItemGroup.MISC));

    ///TODO: Remains unused until Patchouli updates
    ///public static final Item GUIDE_BOOK = new GuideBookItem(new FabricItemSettings().group(ItemGroup.MISC));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(Lycanthropy.MODID, "debug_item"), DEBUG_ITEM);

        ///TODO: Remains unused until Patchouli updates
        ///Registry.register(Registry.ITEM, new Identifier(Lycanthropy.MODID, "guide_book"), GUIDE_BOOK);

        Registry.register(Registry.ITEM, new Identifier(Lycanthropy.MODID, "bone_pile"), new BlockItem(LycanthropyBlockInit.BONE_PILE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Items Registered");
        }
    }

}
