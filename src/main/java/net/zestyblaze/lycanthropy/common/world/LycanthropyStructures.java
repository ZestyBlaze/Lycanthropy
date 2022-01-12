package net.zestyblaze.lycanthropy.common.world;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.world.feature.WolfsbaneFeature;
import net.zestyblaze.lycanthropy.common.world.structures.HunterHouseStructure;

public class LycanthropyStructures {
    public static StructureFeature<StructurePoolFeatureConfig> HUNTER_HOUSE = new HunterHouseStructure(StructurePoolFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> WOLFSBANE = new WolfsbaneFeature(DefaultFeatureConfig.CODEC);

    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new Identifier(Lycanthropy.MODID, "hunter_house"), HUNTER_HOUSE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(
        10, /* average distance apart in chunks between spawn attempts */
        5, /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE */
        399117345 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */))
        .adjustsSurface()
        .register();
    }
}
