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

        /*
         * This is the API you will use to add anything to any biome.
         * This includes spawns, changing the biome's looks, messing with its temperature,
         * adding carvers, spawning new features... etc
         */
        BiomeModifications.addStructure(
        // Add our structure to all biomes that have any of these biome categories. This includes modded biomes.
        // You can filter to certain biomes based on stuff like temperature, scale, precipitation, mod id, etc.
        // See BiomeSelectors's methods for more options or write your own by doing `(context) -> context.whatever() == condition`
        BiomeSelectors.categories(
        Biome.Category.DESERT,
        Biome.Category.EXTREME_HILLS,
        Biome.Category.FOREST,
        Biome.Category.ICY,
        Biome.Category.JUNGLE,
        Biome.Category.PLAINS,
        Biome.Category.SAVANNA,
        Biome.Category.TAIGA),
        // The registry key of our ConfiguredStructure so BiomeModification API can grab it
        // later to tell the game which biomes that your structure can spawn within.
        RegistryKey.of(
        Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(LycanthropyConfiguredStructuresInit.CONFIGURED_HUNTERS_HOUSE))
        );
    }
}
