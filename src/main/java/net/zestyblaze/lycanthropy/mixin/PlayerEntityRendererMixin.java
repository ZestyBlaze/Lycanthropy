package net.zestyblaze.lycanthropy.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.zestyblaze.lycanthropy.api.event.RenderEvents;
import net.zestyblaze.lycanthropy.common.item.FlintlockItem;
import net.zestyblaze.lycanthropy.common.item.GuideBookDevItem;
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
        RenderEvents.PLAYER.invoker().onRender(player, tickDelta, matrixStack, vertexConsumerProvider, light);
        if(LycanthropyUtils.isWerewolf(player)){
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
        if(itemStack.getItem() instanceof GuideBookDevItem) {
            cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
        }
        if (itemStack.getItem() instanceof FlintlockItem) {
            cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
        }
    }

}
