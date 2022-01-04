package net.zestyblaze.lycanthropy.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.renderer.WerewolfEntityRenderer;
import net.zestyblaze.lycanthropy.config.LycanthropyModConfig;

public class LycanthropyClientInit {
    public static void registerRenderer() {
        EntityRendererRegistry.register(LycanthropyEntityTypeInit.WEREWOLF, WerewolfEntityRenderer::new);

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Client Registered");
        }
    }
}
