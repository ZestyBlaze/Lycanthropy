package net.zestyblaze.lycanthropy.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.zestyblaze.lycanthropy.client.LycanthropyRenderLayer;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyStatusEffectsInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.render.entity.LivingEntityRenderer.getOverlay;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Shadow protected abstract float getAnimationCounter(T entity, float tickDelta);

    @Shadow protected M model;

    @Inject(method = "render",at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"))
    private void renderAgain(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo callbackInfo){
        if(MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player && player.hasStatusEffect(LycanthropyStatusEffectsInit.WEREWOLF_VISION) && livingEntity.distanceTo(player) < LycanthropyModConfig.get().werewolfVisionDistance){
            RenderLayer renderLayer = LycanthropyRenderLayer.getWerewolfVisionLayer(this.getTexture(livingEntity));
            matrixStack.scale(2F, 2F, 2F);
            matrixStack.translate(0,-0.5,0);
            if (renderLayer != null) {
                VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
                int o = getOverlay(livingEntity, this.getAnimationCounter(livingEntity, g));
                this.model.render(matrixStack, vertexConsumer, i, o, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
