package com.jdolphin.aabeacon.client;

import com.jdolphin.aabeacon.common.Constants;
import com.jdolphin.aabeacon.common.init.AABEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class AABNeoClientMain {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AABEntities.CRYSTAL, LaserCrystalRenderer::new);
    }
}
