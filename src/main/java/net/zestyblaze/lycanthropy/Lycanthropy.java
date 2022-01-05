package net.zestyblaze.lycanthropy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.registry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lycanthropy implements ModInitializer {
	public static final String MODID = "lycanthropy";
	public static final String MODNAME = "Lycanthropy";
	public static final ItemGroup LYCANTHROPY_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(LycanthropyItemInit.DEBUG_ITEM));

	public static final Logger LOGGER = LogManager.getLogger(MODNAME);

	public static boolean isHaemaLoaded = false;
	public static boolean isBewitchmentLoaded = false;
	@Override
	public void onInitialize() {
		LOGGER.info(MODNAME + " is installed correctly, loading now! Thanks for installing! <3");
		LycanthropyConfigInit.registerConfig();
		LycanthropyItemInit.registerItems();
		LycanthropyBlockInit.registerBlocks();
		LycanthropyEntityTypeInit.initEntityTypes();
		LycanthropyEventInit.registerEvents();

		if (FabricLoader.getInstance().isModLoaded("haema")) {
			isHaemaLoaded = true;
		}
		if (FabricLoader.getInstance().isModLoaded("bewitchment")) {
			isBewitchmentLoaded = true;
		}

		if(LycanthropyModConfig.get().debugMode) {
			LOGGER.info("Lycanthropy: Registry - Mod Fully Loaded!");
		}
	}
}
