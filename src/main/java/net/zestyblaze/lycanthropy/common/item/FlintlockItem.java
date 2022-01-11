package net.zestyblaze.lycanthropy.common.item;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.example.entity.RocketProjectile;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class FlintlockItem extends Item implements IAnimatable, ISyncable {
    public AnimationFactory factory = new AnimationFactory(this);
    public String controllerName = "controller";
    public static final int ANIM_OPEN = 0;
    public int clientUseTicks = 0;
    public FlintlockItem(Settings settings) {
        super(settings.maxCount(1).maxDamage(2));
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(selected && stack.getOrCreateNbt().getBoolean("Fired")){
            clientUseTicks++;
        }
        if(selected && clientUseTicks >= 5){
            clientUseTicks = 0;
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putBoolean("Fired", false);
            stack.setNbt(nbtCompound);
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entityLiving;
            if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
                playerentity.getItemCooldownManager().set(this, 5);
                if (!worldIn.isClient) {
                    RocketProjectile abstractarrowentity = createArrow(worldIn, stack, playerentity);
                    abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(), 0.0F,
                    1.0F * 3.0F, 1.0F);

                    abstractarrowentity.setDamage(2.5);
                    abstractarrowentity.age = 35;
                    abstractarrowentity.hasNoGravity();

                    stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
                    worldIn.spawnEntity(abstractarrowentity);
                    stack.getOrCreateNbt().putBoolean("Fired", true);
                }
                if (!worldIn.isClient) {
                    final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
                    GeckoLibNetwork.syncAnimation(playerentity, this, id, ANIM_OPEN);
                    for (PlayerEntity otherPlayer : PlayerLookup.tracking(playerentity)) {
                        GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
                    }
                }
            }
        }
    }

    public RocketProjectile createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        RocketProjectile arrowentity = new RocketProjectile(worldIn, shooter);
        return arrowentity;
    }



    public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, controllerName, 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_OPEN) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.flintlock_rifle_firing", false));
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }


    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText(
        "Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
        .formatted(Formatting.ITALIC));
    }


}
