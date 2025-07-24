package com.jdolphin.aabeacon.client;

import com.jdolphin.aabeacon.common.init.AABEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class AABFabricClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(AABEntities.CRYSTAL, LaserCrystalRenderer::new);
    }
}
