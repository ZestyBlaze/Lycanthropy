package net.zestyblaze.lycanthropy.api.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.render.entity.PlayerEntityRenderer;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

@Environment(EnvType.CLIENT)
public final class ShootEvents {
    private ShootEvents(){}

    /**
     * Called when flintlock is fired.
     *
     */
    public static final Event<OnShot> ON_SHOT_EVENT = createArrayBacked(OnShot.class, listeners -> () -> {
        for (OnShot listener : listeners) {
            if (listener.onShot()) {
                return true;
            }
        }
        return false;
    });

    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface OnShot {
        boolean onShot();
    }
}