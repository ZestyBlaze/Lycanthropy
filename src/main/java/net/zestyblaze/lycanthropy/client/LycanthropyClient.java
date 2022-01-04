package net.zestyblaze.lycanthropy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.zestyblaze.lycanthropy.registry.LycanthropyClientInit;

@Environment(EnvType.CLIENT)
public class LycanthropyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LycanthropyClientInit.registerRenderer();
    }
}
