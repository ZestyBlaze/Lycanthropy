package net.zestyblaze.lycanthropy.common.world;

import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyBlockInit;

public class LycanthropyOres {
    public static final ConfiguredFeature<?, ?> SILVER_ORE = Feature.ORE.configure(new OreFeatureConfig(
            OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
            LycanthropyBlockInit.SILVER_ORE.getDefaultState(),
            8
            ) // Size of the veins
    );

    public static final PlacedFeature SILVER_ORE_PLACE = SILVER_ORE.withPlacement(
            CountPlacementModifier.of(4), //Times per chunk it generates
            SquarePlacementModifier.of(),
            HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()) //The Min and Max Heights
    );
}
