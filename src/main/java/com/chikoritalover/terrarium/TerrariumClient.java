package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.client.render.BoomerangEntityRenderer;
import com.chikoritalover.terrarium.registry.TerrariumEntityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class TerrariumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(TerrariumEntityType.BOOMERANG, BoomerangEntityRenderer::new);
    }
}
