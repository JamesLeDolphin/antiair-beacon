package com.jdolphin.aabeacon.common;

import com.jdolphin.aabeacon.common.entity.LaserCrystal;
import com.jdolphin.aabeacon.common.init.AABEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.function.BiConsumer;

public class AABFabricMain implements ModInitializer {
    
    @Override
    public void onInitialize() {
        AABMain.init();

        AABEntities.init(bind(BuiltInRegistries.ENTITY_TYPE));

        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (player.getItemInHand(hand).is(Items.END_CRYSTAL)) {
                BlockPos pos = hitResult.getBlockPos();
                if (level.getBlockState(pos).is(Blocks.BEACON)) {
                    BlockPos pos1 = pos.above();
                    LaserCrystal crystal = new LaserCrystal(level);
                    crystal.setPos(pos1.getX() + 0.5D, pos1.getY(), pos1.getZ() + 0.5D);
                    crystal.setShowBottom(false);
                    level.addFreshEntity(crystal);
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, pos1);
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
