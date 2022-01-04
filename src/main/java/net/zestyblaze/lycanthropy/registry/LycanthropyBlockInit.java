package net.zestyblaze.lycanthropy.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.block.BonePileBlock;
import net.zestyblaze.lycanthropy.config.LycanthropyModConfig;

public class LycanthropyBlockInit {
    public static final Block BONE_PILE = new BonePileBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE));

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(Lycanthropy.MODID, "bone_pile"), BONE_PILE);

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Blocks Registered");
        }
    }

}
