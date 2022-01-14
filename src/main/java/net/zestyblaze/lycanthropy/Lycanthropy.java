package net.zestyblaze.lycanthropy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
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
	public static final ItemGroup LYCANTHROPY_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(LycanthropyItemInit.WEREWOLF_TOOTH));

	public static final Logger LOGGER = LogManager.getLogger(MODNAME);

	@Override
	public void onInitialize() {
		LOGGER.info(MODNAME + " is successfully installed! Thanks for installing! <3");
		LycanthropyConfigInit.registerConfig();
		LycanthropyBlockInit.registerBlocks();
		LycanthropyItemInit.registerItems();
		LycanthropyBlockEntityInit.registerBlockEntities();
		LycanthropyEntityTypeInit.initEntityTypes();
		LycanthropyEventInit.registerEvents();
		LycanthropyStatusEffectsInit.registerStatusEffects();
		//LycanthropyStructures.setupAndRegisterStructureFeatures();
		LycanthropyWorldInit.registerConfiguredStructures();
		//LycanthropyWorldInit.addStructureSpawningToDimensionsAndBiomes();
		LycanthropyWorldInit.generateOres();
		LycanthropyCommandInit.registerCommands();
		LycanthropySoundEvents.registerSoundEvents();

		if(LycanthropyModConfig.get().debugMode) {
			LOGGER.info("Lycanthropy: Registry - Mod Fully Loaded!");
		}

	}

}
