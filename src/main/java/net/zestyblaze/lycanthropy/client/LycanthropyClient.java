package net.zestyblaze.lycanthropy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Hand;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.model.WerewolfEntityModel;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.event.RenderEvents;
import net.zestyblaze.lycanthropy.registry.LycanthropyClientInit;
import net.zestyblaze.lycanthropy.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.registry.LycanthropyEntityTypeInit;

@Environment(EnvType.CLIENT)
public class LycanthropyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LycanthropyClientInit.registerRenderer();
        LycanthropyClientInit.registerClientEvents();
    }
}
