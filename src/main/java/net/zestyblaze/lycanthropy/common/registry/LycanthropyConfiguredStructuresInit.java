package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.world.LycanthropyStructures;

public class LycanthropyConfiguredStructuresInit {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_HUNTERS_HOUSE = LycanthropyStructures.HUNTER_HOUSE.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));


    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(Lycanthropy.MODID, "configured_hunter_house"), CONFIGURED_HUNTERS_HOUSE);
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
        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(LycanthropyConfiguredStructuresInit.CONFIGURED_HUNTERS_HOUSE))
        );
    }
}
