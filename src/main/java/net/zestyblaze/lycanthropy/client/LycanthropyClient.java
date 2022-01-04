package net.zestyblaze.lycanthropy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.renderer.WerewolfEntityRenderer;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.registry.LycanthropyEntityTypeInit;

@Environment(EnvType.CLIENT)
public class LycanthropyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(LycanthropyEntityTypeInit.WEREWOLF, WerewolfEntityRenderer::new);
    }
}
