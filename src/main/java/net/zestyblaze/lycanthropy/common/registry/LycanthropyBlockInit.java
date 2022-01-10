package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.block.BonePileBlock;
import net.zestyblaze.lycanthropy.common.block.CageBlock;
import net.zestyblaze.lycanthropy.common.block.SilverBlock;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils.gen;

public class LycanthropyBlockInit {
    public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Block BONE_PILE = register("bone_pile", new BonePileBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE)), true);
    public static final Block SILVER_ORE = register("silver_ore", new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE)), true);
    public static final Block RAW_SILVER_BLOCK = register("raw_silver_block", new Block(FabricBlockSettings.of(Material.METAL)), true);
    public static final Block DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3, 6)), true);
    public static final Block SILVER_BLOCK = register("silver_block", new SilverBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5, 6)), true);

    public static final Block CAGE_BLOCK = register("cage_block", new CageBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.METAL)), true);


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
