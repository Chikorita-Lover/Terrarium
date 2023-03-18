package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.client.render.BoomerangEntityRenderer;
import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import com.chikoritalover.terrarium.registry.TerrariumEntityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class TerrariumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(TerrariumBlocks.EBONWOOD_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TerrariumBlocks.EBONWOOD_TRAPDOOR, RenderLayer.getCutout());

        EntityRendererRegistry.register(TerrariumEntityType.BOOMERANG, BoomerangEntityRenderer::new);
    }
}
