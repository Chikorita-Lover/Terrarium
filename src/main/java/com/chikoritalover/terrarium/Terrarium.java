package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Terrarium implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("terrarium");
    public static final String MODID = "terrarium";
    public static final BlockSetType EBONWOOD_BLOCK_SET = new BlockSetTypeBuilder().build(new Identifier(MODID, "ebonwood"));
    public static final WoodType EBONWOOD_WOOD_TYPE = new WoodTypeBuilder().build(new Identifier(MODID, "ebonwood"), EBONWOOD_BLOCK_SET);

    @Override
    public void onInitialize() {
        TerrariumBlocks.register();
        TerrariumItems.register();

        Block.STATE_IDS.forEach(System.out::println);
    }
}