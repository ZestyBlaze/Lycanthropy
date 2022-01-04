package net.zestyblaze.lycanthropy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.registry.LycanthropyBlockInit;
import net.zestyblaze.lycanthropy.registry.LycanthropyConfigInit;
import net.zestyblaze.lycanthropy.registry.LycanthropyEntityTypeInit;
import net.zestyblaze.lycanthropy.registry.LycanthropyItemInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lycanthropy implements ModInitializer {
	public static final String MODID = "lycanthropy";
	public static final String MODNAME = "Lycanthropy";
	public static final ItemGroup LYCANTHROPY_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(LycanthropyItemInit.DEBUG_ITEM));

	public static final Logger LOGGER = LogManager.getLogger(MODNAME);

	@Override
	public void onInitialize() {
		LOGGER.info(MODNAME + " is installed correctly, loading now! Thanks for installing! <3");
		LycanthropyConfigInit.registerConfig();
		LycanthropyItemInit.registerItems();
		LycanthropyBlockInit.registerBlocks();
		LycanthropyEntityTypeInit.initEntityTypes();

		if(LycanthropyModConfig.get().debugMode) {
			LOGGER.info("Lycanthropy: Registry - Mod Fully Loaded!");
		}
	}
}
