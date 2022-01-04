package net.zestyblaze.lycanthropy.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.component.LycanthropyPlayerComponent;
import net.zestyblaze.lycanthropy.config.LycanthropyModConfig;

public class LycanthropyComponentInit implements EntityComponentInitializer {
    public static final ComponentKey<LycanthropyPlayerComponent> WEREWOLF = ComponentRegistry.getOrCreate(new Identifier(Lycanthropy.MODID, "werewolf"), LycanthropyPlayerComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(WEREWOLF, LycanthropyPlayerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Components Registered");
        }
    }
}
