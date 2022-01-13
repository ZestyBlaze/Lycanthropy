package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.block.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils.gen;

public class LycanthropyBlockInit {
    public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Block BONE_PILE = register("bone_pile", new BonePileBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE)), true);
    public static final Block SILVER_ORE = register("silver_ore", new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE)), true);
    public static final Block DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3, 6)), true);//TODO tweak values
    public static final Block RAW_SILVER_BLOCK = register("raw_silver_block", new Block(FabricBlockSettings.of(Material.METAL)), true);
    public static final Block SILVER_BLOCK = register("silver_block", new SilverBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5, 6)), true);//TODO tweak values
    public static final Block SILVER_SLAB = register("silver_slab", new SlabBlock(FabricBlockSettings.copyOf(SILVER_BLOCK)), true);
    public static final Block SILVER_STAIRS = register("silver_stairs", new SilverStairsBlock(SILVER_BLOCK,FabricBlockSettings.copyOf(SILVER_BLOCK)), true);
    public static final Block SILVER_TILE = register("silver_tile", new SilverBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5, 6)), true);//TODO tweak values
    public static final Block SILVER_TILE_STAIRS = register("silver_tile_stairs", new SilverStairsBlock(SILVER_BLOCK,FabricBlockSettings.copyOf(SILVER_BLOCK)), true);
    public static final Block SILVER_TILE_SLAB = register("silver_tile_slab", new SlabBlock(FabricBlockSettings.copyOf(SILVER_BLOCK)), true);

    public static final Block WOLFSBANE = register("wolfsbane_block", new LycanthropyPlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.BAMBOO_SAPLING).strength(0.3F).nonOpaque().breakInstantly().dynamicBounds()), false);

    public static final Block CAGE_BLOCK = register("cage_block", new CageBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.METAL)), true);
    public static final Block BEAR_TRAP_BLOCK = register("bear_trap_block", new BearTrapBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.METAL)), true);
    public static final Block SILVER_BEAR_TRAP_BLOCK = register("silver_bear_trap_block", new BearTrapBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.METAL)), true);


    /**
     *
     * @param name
     * @param block
     * @param createItem
     * @param <T>
     * @return
     */
    public static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(Lycanthropy.MODID, name));
        if(createItem) {
            ITEMS.put(new BlockItem(block, gen()), BLOCKS.get(block));
        }
        return block;
    }



    public static void registerBlocks() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));



        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Blocks Registered");
        }
    }

}
