package com.chikoritalover.terrarium;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.Blocks;

public class TerrariumEarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();

        String boatEntityType = mappingResolver.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        String block = 'L' + mappingResolver.mapClassName("intermediary", "net.minecraft.class_2248") + ';';
        ClassTinkerers.enumBuilder(boatEntityType, block, String.class).addEnum("EBONWOOD", () -> new Object[]{Blocks.OAK_PLANKS, "ebonwood"}).build();
    }
}
