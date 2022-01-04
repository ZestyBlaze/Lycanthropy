package net.zestyblaze.lycanthropy.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.config.LycanthropyModConfig;

public class LycanthropyConfigInit {
    public static void registerConfig() {
        AutoConfig.register(LycanthropyModConfig.class, GsonConfigSerializer::new);

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Config Registered");
        }
    }
}
