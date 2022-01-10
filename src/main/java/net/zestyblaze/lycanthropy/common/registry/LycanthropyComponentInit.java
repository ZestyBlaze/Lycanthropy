package net.zestyblaze.lycanthropy.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.component.LycanthropyPlayerComponent;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.component.LycanthropyPlayerHungerComponent;
import net.zestyblaze.lycanthropy.common.component.LycanthropyPlayerRageComponent;

public class LycanthropyComponentInit implements EntityComponentInitializer {
    public static final ComponentKey<LycanthropyPlayerComponent> WEREWOLF = ComponentRegistry.getOrCreate(new Identifier(Lycanthropy.MODID, "werewolf"), LycanthropyPlayerComponent.class);
    public static final ComponentKey<LycanthropyPlayerHungerComponent> WEREWOLF_HUNGER = ComponentRegistry.getOrCreate(new Identifier(Lycanthropy.MODID, "werewolf_hunger"), LycanthropyPlayerHungerComponent.class);
    public static final ComponentKey<LycanthropyPlayerRageComponent> WEREWOLF_RAGE = ComponentRegistry.getOrCreate(new Identifier(Lycanthropy.MODID, "werewolf_rage"), LycanthropyPlayerRageComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(WEREWOLF, LycanthropyPlayerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(WEREWOLF_HUNGER, LycanthropyPlayerHungerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(WEREWOLF_RAGE, LycanthropyPlayerRageComponent::new, RespawnCopyStrategy.NEVER_COPY);

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Components Registered");
        }
    }


}
