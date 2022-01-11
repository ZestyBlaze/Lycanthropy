package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;

public class LycanthropyEventInit {
    public static void registerEvents() {
        ServerEntityEvents.ENTITY_LOAD.register((player, world) -> {
            if(LycanthropyModConfig.get().werewolfStart) {
                LycanthropyComponentInit.WEREWOLF.maybeGet(player).ifPresent(werewolfComponent -> werewolfComponent.setCanBecomeWerewolf(true));
            }
        });

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Events Registered");
        }
    }
}
