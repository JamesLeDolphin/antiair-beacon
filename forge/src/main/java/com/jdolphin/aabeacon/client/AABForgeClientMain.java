package com.jdolphin.aabeacon.client;

import com.jdolphin.aabeacon.common.Constants;
import com.jdolphin.aabeacon.common.init.AABEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AABForgeClientMain {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AABEntities.CRYSTAL, LaserCrystalRenderer::new);
    }
}
