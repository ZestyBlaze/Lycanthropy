package net.zestyblaze.lycanthropy.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

public final class TransformationEvents {
    private TransformationEvents(){}

    /**
     * Called when the player transforms
     */
    public static final Event<OnTransformation> ON_TRANSFORMATION_EVENT = createArrayBacked(OnTransformation.class, listeners -> (player) -> {
        for (OnTransformation listener : listeners) {
            listener.onTransformation(player);
        }
    });

    public static final Event<CancelTransformation> CANCEL_TRANSFORMATION_EVENT = createArrayBacked(CancelTransformation.class, listeners -> (player) -> {
        for (CancelTransformation listener : listeners) {
            if (listener.shouldCancel(player)) {
                return true;
            }
        }
        return false;
    });

    /**
     * Called when the player gains the ability to transform
     */
    public static final Event<OnCanTransform> ON_CAN_TRANSFORM_EVENT = createArrayBacked(OnCanTransform.class, listeners -> (player) -> {
        for (OnCanTransform listener : listeners) {
            listener.onCanTransform(player);
        }
    });

    @FunctionalInterface
    public interface OnTransformation {
        void onTransformation(PlayerEntity player);
    }

    @FunctionalInterface
    public interface CancelTransformation {
        boolean shouldCancel(PlayerEntity player);
    }

    @FunctionalInterface
    public interface OnCanTransform {
        void onCanTransform(PlayerEntity player);
    }
}