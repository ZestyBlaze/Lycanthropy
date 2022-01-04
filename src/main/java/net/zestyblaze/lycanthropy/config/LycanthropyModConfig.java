package net.zestyblaze.lycanthropy.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.zestyblaze.lycanthropy.Lycanthropy;

@Config(name = Lycanthropy.MODID)
public class LycanthropyModConfig implements ConfigData {

    public boolean debugMode = false;

    public static LycanthropyModConfig get() {
        return AutoConfig.getConfigHolder(LycanthropyModConfig.class).getConfig();
    }
}
