package net.zestyblaze.lycanthropy.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyBlockInit;

public class WolfsbaneFeature extends Feature<DefaultFeatureConfig> {
    public WolfsbaneFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos topPos = context.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, context.getOrigin());
        BlockPos belowPos = topPos.down();
        if (context.getWorld().getBlockState(belowPos).getBlock() == Blocks.GRASS_BLOCK)
            context.getWorld().setBlockState(topPos, LycanthropyBlockInit.WOLFSBANE.getDefaultState(), 3);
        return true;
    }
}
