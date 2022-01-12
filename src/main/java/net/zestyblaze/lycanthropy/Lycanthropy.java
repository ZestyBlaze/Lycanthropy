package net.zestyblaze.lycanthropy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
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
		LOGGER.info(MODNAME + " is installed correctly, loading now! Thanks for installing! <3");
		LycanthropyConfigInit.registerConfig();
		LycanthropyItemInit.registerItems();
		LycanthropyBlockInit.registerBlocks();
		LycanthropyBlockEntityInit.registerBlockEntities();
		LycanthropyEntityTypeInit.initEntityTypes();
		LycanthropyEventInit.registerEvents();
		LycanthropyStatusEffectsInit.registerStatusEffects();
		LycanthropyCommandInit.registerCommands();
		LycanthropySoundEvents.registerSoundEvents();
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			LycanthropyComponentInit.WEREWOLF.get(newPlayer).setIsWerewolf(false);
		});


		if(LycanthropyModConfig.get().debugMode) {
			LOGGER.info("Lycanthropy: Registry - Mod Fully Loaded!");
		}

	}

}
