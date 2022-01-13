package net.zestyblaze.lycanthropy.common.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.entity.ai.RangedAttackGoal;
import net.zestyblaze.lycanthropy.common.item.SilverBulletItem;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyItemInit;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class HunterEntity extends MerchantEntity implements IAnimatable, Angerable, RangedAttackMob {
    AnimationFactory factory = new AnimationFactory(this);

    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(HunterEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(160, 180);

    private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(HunterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private UUID angryAt;

    public HunterEntity(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
        this.getNavigation().setCanSwim(true);
        this.ignoreCameraFrustum = true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return true;
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
            if(this.isAttacking() && !(this.dead || this.getHealth() < 0.01 || this.isDead())){
                builder.addAnimation("animation.hunter.attackwalking", true);
            }else{
                builder.addAnimation("animation.hunter.walking", true);
            }
        }else{
            if(this.isAttacking() && !(this.dead || this.getHealth() < 0.01 || this.isDead())){
                builder.addAnimation("animation.hunter.attacking", true);
            }else{
                builder.addAnimation("animation.hunter.idle", true);
            }

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
        goalSelector.add(1, new RangedAttackGoal<>(this, 1.0D, 20, 15.0F));
        goalSelector.add(3, new WanderAroundFarGoal(this, 1));
        goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8));
        goalSelector.add(4, new LookAroundGoal(this));
        targetSelector.add(0, new RevengeGoal(this));
        targetSelector.add(1, new ActiveTargetGoal<>(this, WerewolfEntity.class, true));
        targetSelector.add(2, new ActiveTargetGoal(this, HostileEntity.class, 5, false, false, (entity) -> entity instanceof Monster && !(entity instanceof CreeperEntity)));
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SHOOTING, false);
        this.dataTracker.startTracking(ANGER_TIME, 0);
        super.initDataTracker();
    }

    public void setShooting(boolean shooting) {
        this.dataTracker.set(SHOOTING, shooting);
    }


    private ItemStack makeInitialWeapon() {
        return new ItemStack(LycanthropyItemInit.FLINTLOCK_RIFLE);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        super.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }


    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getArrowType(this.getStackInHand(
        ProjectileUtil.getHandPossiblyHolding(this, this.getEquippedStack(EquipmentSlot.MAINHAND).getItem())));
        SilverBulletEntity SilverBulletEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(1 / 3D) - SilverBulletEntity.getY();
        double f = target.getZ() - this.getZ();
        float g = MathHelper.sqrt((float) (d * d + f * f));
        SilverBulletEntity.setVelocity(d, e + g * 0.05F, f, 1.6F, 0.0F);
        this.world.spawnEntity(SilverBulletEntity);
    }

    protected SilverBulletEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return HunterEntity.createArrowProjectile(this, arrow, damageModifier);
    }

    public static SilverBulletEntity createArrowProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
        SilverBulletItem arrowItem = (SilverBulletItem) ((stack.getItem() instanceof SilverBulletItem ? stack.getItem() : LycanthropyItemInit.BULLETS));
        SilverBulletEntity persistentProjectileEntity = arrowItem.createArrow(entity.world, stack, entity);
        persistentProjectileEntity.applyEnchantmentEffects(entity, damageModifier);

        return persistentProjectileEntity;
    }

    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
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


    @Override
    protected void afterUsing(TradeOffer offer) {

    }

    @Override
    protected void fillRecipes() {

    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        VillagerType villagerType = VillagerType.forBiome(world.getBiomeKey(this.getBlockPos()));
        VillagerEntity villagerEntity = new VillagerEntity(EntityType.VILLAGER, world, villagerType);
        villagerEntity.initialize(world, world.getLocalDifficulty(villagerEntity.getBlockPos()), SpawnReason.BREEDING, null, null);
        return villagerEntity;
    }
}
