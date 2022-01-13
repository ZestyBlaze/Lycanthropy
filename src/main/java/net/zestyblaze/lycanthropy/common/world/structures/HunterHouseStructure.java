package net.zestyblaze.lycanthropy.common.world.structures;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyEntityTypeInit;
import org.apache.logging.log4j.Level;

import java.util.Optional;

public class HunterHouseStructure extends StructureFeature<StructurePoolFeatureConfig> {
    public HunterHouseStructure(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, HunterHouseStructure::createPiecesGenerator, PostPlacementProcessor.EMPTY);
    }
    public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool.of(
    );

    public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_CREATURES = Pool.of(
    new SpawnSettings.SpawnEntry(LycanthropyEntityTypeInit.HUNTER_VILLAGER, 30, 10, 15)
    );

    private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        BlockPos spawnXZPosition = context.chunkPos().getCenterAtY(0);

        // Grab height of land. Will stop at first non-air block.
        int landHeight = context.chunkGenerator().getHeightInGround(spawnXZPosition.getX(), spawnXZPosition.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());

        // Grabs column of blocks at given position. In overworld, this column will be made of stone, water, and air.
        // In nether, it will be netherrack, lava, and air. End will only be endstone and air. It depends on what block
        // the chunk generator will place for that dimension.
        VerticalBlockSample columnOfBlocks = context.chunkGenerator().getColumnSample(spawnXZPosition.getX(), spawnXZPosition.getZ(), context.world());

        // Combine the column of blocks with land height, and you get the top block itself which you can test.
        BlockState topBlock = columnOfBlocks.getState(landHeight);

        // Now we test to make sure our structure is not spawning on water or other fluids.
        // You can do height check instead too to make it spawn at high elevations.
        return topBlock.getFluidState().isEmpty(); //landHeight > 100;
    }

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!HunterHouseStructure.isFeatureChunk(context)) {
            return Optional.empty();
        }

        StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(
        () -> context.registryManager().get(Registry.STRUCTURE_POOL_KEY)
        .get(new Identifier(Lycanthropy.MODID, "hunter_house/start_pool")), 10);

        StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
        context.chunkGenerator(),
        context.biomeSource(),
        context.seed(),
        context.chunkPos(),
        newConfig,
        context.world(),
        context.validBiome(),
        context.structureManager(),
        context.registryManager()
        );

        BlockPos blockpos = context.chunkPos().getCenterAtY(0);

        Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator =
        StructurePoolBasedGenerator.generate(
        newContext, // Used for StructurePoolBasedGenerator to get all the proper behaviors done.
        PoolStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
        true // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
        );


        if(structurePiecesGenerator.isPresent()) {
            // This is returning the coordinates of the center starting piece.
            Lycanthropy.LOGGER.log(Level.DEBUG, "Rundown House at " + blockpos);
        }

        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return structurePiecesGenerator;
    }


}
