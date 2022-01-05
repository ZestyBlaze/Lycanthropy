package net.zestyblaze.lycanthropy.common.entity;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.utils.LycanthropyAnimationController;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WerewolfEntity extends WerewolfBaseEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);
    private ItemStack mainHand = ItemStack.EMPTY;
    private ItemStack offHand = ItemStack.EMPTY;

    public WerewolfEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
        .add(EntityAttributes.GENERIC_ARMOR, 20)
        .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 10)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

    public void setItemStackHands(ItemStack itemStack, ItemStack itemStack2){
        this.mainHand = itemStack;
        this.offHand = itemStack2;
    }

    private <E extends IAnimatable> PlayState devMovement(AnimationEvent<E> animationEvent) {
        final AnimationController animationController = animationEvent.getController();
        //Create a builer to stack animations in.
        AnimationBuilder builder = new AnimationBuilder();

        //Normal Speed
        lycanthropyAnimationController.speed = 1;


        WerewolfEntity entity = this;
        Vec3d motion = new Vec3d(entity.getX() - entity.prevX, entity.getY() - entity.prevY, entity.getZ() - entity.prevZ);
        boolean isMovingHorizontal = Math.sqrt(Math.pow(motion.x, 2) + Math.pow(motion.z, 2)) > 0.005;

        if (entity.isSleeping()) { //TODO maybe add special "resting" condition/ability
            builder.addAnimation("animation.werewolf.sleep", true);
        }else if (entity.getPose() == EntityPose.SWIMMING) {
            builder.addAnimation("animation.werewolf.swim", true);
        }else if (!entity.isOnGround() && motion.getY() < 0) {
            if ((entity.fallDistance <= 4) && !entity.isClimbing()) {
                builder.addAnimation("animation.werewolf.fall", false);
            }
        }else if (entity.isSneaking()) {
            if (isMovingHorizontal) {
                builder.addAnimation("animation.werewolf.sneak_walk", true);

            } else {
                builder.addAnimation("animation.werewolf.sneak", true);
            }
        }else {
            var v = motion.x * motion.x + motion.z * motion.z;
            if (entity.isSprinting()) {
                lycanthropyAnimationController.speed = 1 + ((double)MathHelper.sqrt((float) v) / 10);
                builder.addAnimation("animation.werewolf.run", true);

            }else if (isMovingHorizontal || animationEvent.isMoving()) {
                lycanthropyAnimationController.speed = 1 + ((double)MathHelper.sqrt((float) v) / 10);
                builder.addAnimation("animation.werewolf.walk", true);
            }
        }
        if(animationEvent.getController().getCurrentAnimation() == null || builder.getRawAnimationList().size() <= 0){
            builder.addAnimation( "animation.werewolf.idle", true);
        }
        animationController.setAnimation(builder);
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(lycanthropyAnimationController);
    }
    LycanthropyAnimationController lycanthropyAnimationController = new LycanthropyAnimationController(this, "DevMovement", 2, this::devMovement);

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
        goalSelector.add(2, new WanderAroundFarGoal(this, 1));
        goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
        goalSelector.add(3, new LookAroundGoal(this));
        targetSelector.add(0, new RevengeGoal(this));
    }
}
