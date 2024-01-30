package com.chikoritalover.terrarium;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class TerrariumEarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();
    }
}
