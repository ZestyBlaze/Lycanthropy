package net.zestyblaze.lycanthropy.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

@SuppressWarnings("ALL")
public class HunterEntity extends HostileEntity implements IAnimatable, Angerable {
    AnimationFactory factory = new AnimationFactory(this);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int angerTime;
    private UUID angryAt;
    public HunterEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
        .add(EntityAttributes.GENERIC_ARMOR, 20)
        .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 10)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }
    private <E extends IAnimatable> PlayState devMovement(AnimationEvent<E> animationEvent) {
        final AnimationController animationController = animationEvent.getController();
        //Create a builder to stack animations in.
        AnimationBuilder builder = new AnimationBuilder();
        if(animationEvent.isMoving()){
            builder.addAnimation("animation.hunter.walking", true);
        }else{
            builder.addAnimation("animation.hunter.idle", true);

        }
        animationController.setAnimation(builder);
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "DevMovement", 2, this::devMovement));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        if (type == EntityType.PLAYER) {
            return false;
        } else {
            return type != EntityType.CREEPER && super.canTarget(type);
        }
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(2, new WanderAroundFarGoal(this, 1));
        goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
        goalSelector.add(3, new LookAroundGoal(this));
        targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MobEntity.class, 5, false, false, (entity) -> entity instanceof Monster && !(entity instanceof CreeperEntity)));
    }



    public void setAngerTime(int ticks) {
        this.angerTime = ticks;
    }

    public int getAngerTime() {
        return this.angerTime;
    }

    public void setAngryAt(@Nullable UUID uuid) {
        this.angryAt = uuid;
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }


    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }


}
