package net.zestyblaze.lycanthropy.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.zestyblaze.lycanthropy.common.block.CageBlock;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyBlockEntityInit;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static net.zestyblaze.lycanthropy.common.block.CageBlock.OPEN;

public class CageBlockEntity extends BlockEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);
    public CageBlockEntity(BlockPos pos, BlockState state) {
        super(LycanthropyBlockEntityInit.CAGE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controllerName", 0, this::predicate));
    }

    public <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(event.getAnimatable().getWorld().getBlockState(event.getAnimatable().getPos()).getBlock() instanceof CageBlock && event.getAnimatable().getWorld().getBlockState(event.getAnimatable().getPos()).get(OPEN)){
            builder.addAnimation("animation.cage.opening", false);
            builder.addAnimation("animation.cage.open", true);
        }else{
            builder.addAnimation("animation.cage.closing", false);
            builder.addAnimation("animation.cage.idle", true);
        }
        event.getController().setAnimation(builder);

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
