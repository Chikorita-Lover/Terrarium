package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import com.chikoritalover.terrarium.registry.TerrariumEntityType;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import com.chikoritalover.terrarium.registry.TerrariumSoundEvents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Terrarium implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("terrarium");
	public static final String MODID = "terrarium";

	@Override
	public void onInitialize() {
		TerrariumBlocks.register();
		TerrariumBlocks.registerFlammableBlocks();
		TerrariumBlocks.registerStrippableBlockPairs();
		TerrariumEntityType.register();
		TerrariumItems.register();
		TerrariumItems.registerFuels();
		TerrariumSoundEvents.register();
	}
}