package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.world.LycanthropyStructures;


public class LycanthropyWorldInit {
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_HUNTERS_HOUSE = LycanthropyStructures.HUNTER_HOUSE.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));

    public static final ConfiguredFeature<?,?> CONFIGURED_WOLFSBANE = Feature.FLOWER.configure(new RandomPatchFeatureConfig(
    64, //Chance, higher = more
    6, //spread XZ
    2, //Spread Y
    () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(LycanthropyBlockInit.WOLFSBANE))).withInAirFilter())
    );

    public static final PlacedFeature WOLFSBANE_FEATURE = PlacedFeatures.register("wolfsbane_feature", CONFIGURED_WOLFSBANE.withPlacement(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));

    public static void registerConfiguredStructures() {
        //Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE; //TODO add this when nbt is present
        //Registry.register(registry, new Identifier(Lycanthropy.MODID, "configured_hunter_house"), CONFIGURED_HUNTERS_HOUSE);//TODO add this when nbt is present
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Lycanthropy.MODID, "wolfsbane_feature"), CONFIGURED_WOLFSBANE);
    }

    public static void addStructureSpawningToDimensionsAndBiomes() {
        BiomeModifications.addStructure(
        BiomeSelectors.categories(
        Biome.Category.DESERT,
        Biome.Category.EXTREME_HILLS,
        Biome.Category.FOREST,
        Biome.Category.ICY,
        Biome.Category.JUNGLE,
        Biome.Category.PLAINS,
        Biome.Category.SAVANNA,
        Biome.Category.TAIGA),
        RegistryKey.of(
        Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(LycanthropyWorldInit.CONFIGURED_HUNTERS_HOUSE))
        );
    }
}
