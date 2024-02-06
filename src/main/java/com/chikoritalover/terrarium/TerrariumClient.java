package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class TerrariumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(TerrariumBlocks.EBONWOOD_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                TerrariumBlocks.EBONWOOD_DOOR, TerrariumBlocks.EBONWOOD_TRAPDOOR, TerrariumBlocks.EBONWOOD_SAPLING, TerrariumBlocks.POTTED_EBONWOOD_SAPLING
        );
    }
}
