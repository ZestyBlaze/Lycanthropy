package net.zestyblaze.lycanthropy.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.registry.LycanthropyEntityTypeInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void render(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo callbackInfo) {
        if(LycanthropyComponentInit.WEREWOLF.get(player).isWerewolf()){
            WerewolfEntity entity = LycanthropyEntityTypeInit.WEREWOLF.create(player.world);
            if (entity != null) {
                entity.age = player.age;
                entity.hurtTime = player.hurtTime;
                entity.maxHurtTime = Integer.MAX_VALUE;
                entity.limbDistance = player.limbDistance;
                entity.lastLimbDistance = player.lastLimbDistance;
                entity.limbAngle = player.limbAngle;
                entity.headYaw = player.headYaw;
                entity.prevHeadYaw = player.prevHeadYaw;
                entity.bodyYaw = player.bodyYaw;
                entity.prevBodyYaw = player.prevBodyYaw;
                entity.handSwinging = player.handSwinging;
                entity.handSwingTicks = player.handSwingTicks;
                entity.handSwingProgress = player.handSwingProgress;
                entity.lastHandSwingProgress = player.lastHandSwingProgress;
                entity.setPitch(player.getPitch());
                entity.prevPitch = player.prevPitch;
                entity.preferredHand = player.preferredHand;
                entity.setStackInHand(Hand.MAIN_HAND, player.getMainHandStack());
                entity.setStackInHand(Hand.OFF_HAND, player.getOffHandStack());
                entity.setCurrentHand(player.getActiveHand() == null ? Hand.MAIN_HAND : player.getActiveHand());
                entity.setSneaking(player.isSneaking());
                entity.setPose(player.getPose());
                MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
            }
            callbackInfo.cancel();
        }
    }
}
