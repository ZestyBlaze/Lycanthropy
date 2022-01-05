package net.zestyblaze.lycanthropy.common.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.item.DebugItem;
import net.zestyblaze.lycanthropy.common.item.GuideBookDevItem;
import net.zestyblaze.lycanthropy.common.item.GuideBookItem;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils.gen;

public class LycanthropyItemInit {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Item DEBUG_ITEM = register("debug_item", new DebugItem(gen()));
    public static final Item GUIDE_BOOK = register("guide_book", new GuideBookItem(gen()));
    public static final Item GUIDE_BOOK_DEV = register("guide_book_dev", new GuideBookDevItem(gen()));


    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, new Identifier(Lycanthropy.MODID, name));
        return item;
    }

    public static void registerItems() {
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Items Registered");
        }
    }
}
