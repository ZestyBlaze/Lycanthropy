package net.zestyblaze.lycanthropy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyDamageSources;

public class SilverBlock extends Block {
    public SilverBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        LycanthropyComponentInit.WEREWOLF.maybeGet(entity).ifPresent(werewolf -> {
            if(werewolf.getIsWerewolf()) {
                entity.damage(LycanthropyDamageSources.SILVER, 4.0f);
            }
        });
        super.onSteppedOn(world, pos, state, entity);
    }
}
