package net.zestyblaze.lycanthropy.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.common.item.FlintlockItem;
import net.zestyblaze.lycanthropy.common.item.GuideBookItem;
import net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    /**
     * This is where RenderEvents hooks. As well as disabling player rendering while player is werewolf
     * @param player clientsided player entity
     * @param yaw
     * @param tickDelta
     * @param matrixStack
     * @param vertexConsumerProvider
     * @param light
     * @param callbackInfo
     */
    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void render(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo callbackInfo) {
        WerewolfEntity werewolfEntity = LycanthropyUtils.getWerewolf(player);
        if(werewolfEntity!=null){
            werewolfEntity.age = player.age;
            werewolfEntity.hurtTime = player.hurtTime;
            werewolfEntity.maxHurtTime = Integer.MAX_VALUE;
            werewolfEntity.limbDistance = player.limbDistance;
            werewolfEntity.lastLimbDistance = player.lastLimbDistance;
            werewolfEntity.limbAngle = player.limbAngle;
            werewolfEntity.headYaw = player.headYaw;
            werewolfEntity.prevHeadYaw = player.prevHeadYaw;
            werewolfEntity.bodyYaw = player.bodyYaw;
            werewolfEntity.prevBodyYaw = player.prevBodyYaw;
            werewolfEntity.handSwinging = player.handSwinging;
            werewolfEntity.handSwingTicks = player.handSwingTicks;
            werewolfEntity.handSwingProgress = player.handSwingProgress;
            werewolfEntity.lastHandSwingProgress = player.lastHandSwingProgress;
            werewolfEntity.setPitch(player.getPitch());
            werewolfEntity.prevPitch = player.prevPitch;
            werewolfEntity.preferredHand = player.preferredHand;
            werewolfEntity.setStackInHand(Hand.MAIN_HAND, player.getMainHandStack());
            werewolfEntity.setStackInHand(Hand.OFF_HAND, player.getOffHandStack());
            werewolfEntity.setCurrentHand(player.getActiveHand() == null ? Hand.MAIN_HAND : player.getActiveHand());
            werewolfEntity.setSneaking(player.isSneaking());
            werewolfEntity.motionCalc = new Vec3d(player.getX()-player.prevX, player.getY()-player.prevY,player.getZ()-player.prevZ);
            werewolfEntity.isSneaking();
            werewolfEntity.forwardSpeed=player.forwardSpeed;
            werewolfEntity.setPose(player.getPose());
            werewolfEntity.setSprinting(player.isSprinting());
            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(werewolfEntity).render(werewolfEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
            callbackInfo.cancel();
        }
    }

    /**
     * When the player is holding the 3d model of the guide book, we use the Block pose
     * @param abstractClientPlayerEntity
     * @param hand
     * @param cir
     */
    @Inject(at = @At("HEAD"), method = "getArmPose", cancellable = true)
    @Environment(EnvType.CLIENT)
    private static void getArmPose(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack itemStack = abstractClientPlayerEntity.getStackInHand(hand);
        if(itemStack.getItem() instanceof GuideBookItem) {
            cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
        }
        if (itemStack.getItem() instanceof FlintlockItem) {
            cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
        }
    }



}
