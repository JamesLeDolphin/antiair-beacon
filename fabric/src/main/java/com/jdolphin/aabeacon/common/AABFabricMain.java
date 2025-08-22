package com.jdolphin.aabeacon.common;

import com.jdolphin.aabeacon.common.entity.LaserCrystal;
import com.jdolphin.aabeacon.common.init.AABEntities;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.fml.config.ModConfig;

import java.util.function.BiConsumer;

public class AABFabricMain implements ModInitializer {
    
    @Override
    public void onInitialize() {
        AABMain.init();

        AABEntities.init(bind(BuiltInRegistries.ENTITY_TYPE));
        ForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, AABCommonConfig.SPEC, "aabeacon-common.toml");

        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (player.getItemInHand(hand).is(Items.END_CRYSTAL)) {
                BlockPos pos = hitResult.getBlockPos();
                if (level.getBlockState(pos).is(Blocks.BEACON)) {
                    LaserCrystal.create(level, pos, player);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
