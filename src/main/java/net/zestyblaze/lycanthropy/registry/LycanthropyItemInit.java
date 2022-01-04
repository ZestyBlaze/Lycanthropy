package net.zestyblaze.lycanthropy.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.item.DebugItem;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.zestyblaze.lycanthropy.utils.LycanthropyUtils.gen;

public class LycanthropyItemInit {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Item DEBUG_ITEM = register("debug_item", new DebugItem(gen()));

    ///TODO: Remains unused until Patchouli updates
    ///public static final Item GUIDE_BOOK = new GuideBookItem(new FabricItemSettings().group(ItemGroup.MISC));

    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, new Identifier(Lycanthropy.MODID, name));
        return item;
    }

    public static void registerItems() {
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));

        ///TODO: Remains unused until Patchouli updates
        ///Registry.register(Registry.ITEM, new Identifier(Lycanthropy.MODID, "guide_book"), GUIDE_BOOK);

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Items Registered");
        }
    }
}
