package net.zestyblaze.lycanthropy.common.registry;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.item.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils.gen;

public class LycanthropyItemInit {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Item DEBUG_ITEM = register("debug_item", new DebugItem(gen()));
    public static final Item GUIDE_BOOK = register(LycanthropyModConfig.get().modelBook3D ? "guide_book_dev" : "guide_book", LycanthropyModConfig.get().modelBook3D ? new GuideBookDevItem(gen()) : new GuideBookItem(gen()));

    public static final WolfpeltArmorItem WOLFPELT_HEAD = register("wolfpelt_head", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, gen()));
    public static final WolfpeltArmorItem WOLFPELT_CHEST = register("wolfpelt_chest", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, gen()));
    public static final WolfpeltArmorItem WOLFPELT_LEGGINGS = register("wolfpelt_leggings", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, gen()));
    public static final WolfpeltArmorItem WOLFPELT_BOOTS = register("wolfpelt_boots", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, gen()));

    public static final Item SILVER_INGOT = register("silver_ingot", new Item(gen()));
    public static final Item SILVER_CHUNK = register("silver_chunk", new Item(gen()));
    public static final Item SILVER_NUGGET = register("silver_nugget", new Item(gen()));

    public static final SwordItem SILVER_SWORD = register("silver_sword", new SwordItem(ToolMaterials.IRON, 2, 2, gen()));//TODO change stats
    public static final LycanthropyAxeItem SILVER_AXE = register("silver_axe", new LycanthropyAxeItem(ToolMaterials.IRON, 2, 2, gen())); //TODO change stats
    public static final FlintlockItem FLINTLOCK_RIFLE = register("flinklock_rifle", new FlintlockItem(gen()));


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
