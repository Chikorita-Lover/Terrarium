package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class TerrariumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(TerrariumBlocks.EBONWOOD_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TerrariumBlocks.EBONWOOD_TRAPDOOR, RenderLayer.getCutout());
    }
}
