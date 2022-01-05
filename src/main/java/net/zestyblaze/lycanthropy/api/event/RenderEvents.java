package net.zestyblaze.lycanthropy.api.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

@Environment(EnvType.CLIENT)
public final class RenderEvents {
    private RenderEvents(){

    }

    /**
     * Called when the player is rendering.
     * Use this event instead of injecting to the HEAD of {@link PlayerEntityRenderer#render}
     */
    public static final Event<OnRender> PLAYER = createArrayBacked(OnRender.class, listeners -> (player, tickDelta, matrixStack, vertexConsumerProvider, light) -> {
        for (OnRender listener : listeners) {
            listener.onRender(player, tickDelta, matrixStack, vertexConsumerProvider, light);
        }
    });

    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface OnRender {
        void onRender(AbstractClientPlayerEntity player, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light);
    }
}