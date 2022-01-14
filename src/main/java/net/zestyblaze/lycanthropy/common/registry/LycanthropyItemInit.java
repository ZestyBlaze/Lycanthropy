package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.item.*;
import net.zestyblaze.lycanthropy.common.item.tool.LycanthropyAxeItem;
import net.zestyblaze.lycanthropy.common.item.tool.LycanthropyHoeItem;
import net.zestyblaze.lycanthropy.common.item.tool.LycanthropyPickaxeItem;
import net.zestyblaze.lycanthropy.common.item.tool.material.SilverToolMaterial;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils.gen;

public class LycanthropyItemInit {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    public static final Item GUIDE_BOOK = register("guide_book", new GuideBookItem(new FabricItemSettings().rarity(Rarity.EPIC).group(Lycanthropy.LYCANTHROPY_GROUP)));

    public static final Item WOLF_PELT = register("wolf_pelt", new Item(gen()));
    public static final Item WOLFSBANE = register("wolfsbane", new WolfsbaneItem(gen()));
    public static final Item WEREWOLF_TOOTH = register("werewolf_tooth", new WerewolfToothItem(gen()));

    public static final SilverBulletItem BULLETS = register("bullets",new SilverBulletItem(1.5F));

    public static final WolfpeltArmorItem WOLF_PELT_HEAD = register("wolf_pelt_head", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, gen()));
    public static final WolfpeltArmorItem WOLF_PELT_CHEST = register("wolf_pelt_chest", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, gen()));
    public static final WolfpeltArmorItem WOLF_PELT_LEGGINGS = register("wolf_pelt_leggings", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, gen()));
    public static final WolfpeltArmorItem WOLF_PELT_BOOTS = register("wolf_pelt_boots", new WolfpeltArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, gen()));

    public static final Item SILVER_INGOT = register("silver_ingot", new Item(gen()));
    public static final Item SILVER_CHUNK = register("silver_chunk", new Item(gen()));
    public static final Item SILVER_NUGGET = register("silver_nugget", new Item(gen()));

    //TODO change all tool stats
    public static final ToolItem SILVER_SWORD = register("silver_sword", new SwordItem(SilverToolMaterial.INSTANCE, 2, 2, gen()));
    public static final ToolItem SILVER_SHOVEL = register("silver_shovel", new ShovelItem(SilverToolMaterial.INSTANCE, 2, 2, gen()));
    public static final ToolItem SILVER_PICKAXE = register("silver_pickaxe", new LycanthropyPickaxeItem(SilverToolMaterial.INSTANCE, 2, 2, gen()));
    public static final ToolItem SILVER_AXE = register("silver_axe", new LycanthropyAxeItem(SilverToolMaterial.INSTANCE, 2, 2, gen()));
    public static final ToolItem SILVER_HOE = register("silver_hoe", new LycanthropyHoeItem(SilverToolMaterial.INSTANCE, 2, 2, gen()));

    public static final FlintlockItem FLINTLOCK_RIFLE = register("flintlock_rifle", new FlintlockItem(gen()));

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
