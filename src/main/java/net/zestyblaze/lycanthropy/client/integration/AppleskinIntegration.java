package net.zestyblaze.lycanthropy.client.integration;

import net.minecraft.client.MinecraftClient;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import squeek.appleskin.api.AppleSkinApi;
import squeek.appleskin.api.event.HUDOverlayEvent;

public class AppleskinIntegration implements AppleSkinApi {
    @Override
    public void registerEvents() {
        HUDOverlayEvent.Saturation.EVENT.register(saturation -> LycanthropyComponentInit.WEREWOLF.maybeGet(MinecraftClient.getInstance().player).ifPresent(lycanthropyPlayerComponent -> {
            if(lycanthropyPlayerComponent.getIsWerewolf()){
                saturation.isCanceled = true;
            }
        }));

        HUDOverlayEvent.Exhaustion.EVENT.register(exhaustion -> LycanthropyComponentInit.WEREWOLF.maybeGet(MinecraftClient.getInstance().player).ifPresent(lycanthropyPlayerComponent -> {
            if(lycanthropyPlayerComponent.getIsWerewolf()){
                exhaustion.isCanceled = true;
            }
        }));
    }
}
