package net.zestyblaze.lycanthropy.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.zestyblaze.lycanthropy.api.event.RenderEvents;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
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
        RenderEvents.PLAYER.invoker().onRender(player, tickDelta, matrixStack, vertexConsumerProvider, light);
        if(LycanthropyComponentInit.WEREWOLF.get(player).isWerewolf()){
            callbackInfo.cancel();
        }
    }
}
