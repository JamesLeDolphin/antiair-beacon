package com.jdolphin.aabeacon.common;

import com.jdolphin.aabeacon.common.entity.LaserCrystal;
import com.jdolphin.aabeacon.common.init.AABEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class AABeaconNeoMain {
    
    public AABeaconNeoMain(ModContainer container) {
        AABMain.init();
        IEventBus bus = container.getEventBus();
        bind(bus, Registries.ENTITY_TYPE, AABEntities::init);
        NeoForge.EVENT_BUS.register(new Event());
        container.registerConfig(ModConfig.Type.COMMON, AABCommonConfig.SPEC, "aabeacon-common.toml");
    }

    private static <T> void bind(IEventBus bus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        bus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

    public static class Event {

        @SubscribeEvent
        public  void useEvent(PlayerInteractEvent.RightClickBlock event) {
            BlockPos pos = event.getPos();
            Level level = event.getLevel();
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();

            if (player.getItemInHand(hand).is(Items.END_CRYSTAL)) {
                if (level.getBlockState(pos).is(Blocks.BEACON)) {
                    BlockPos pos1 = pos.above();
                    LaserCrystal crystal = new LaserCrystal(level);
                    crystal.setPos(pos1.getX() + 0.5D, pos1.getY(), pos1.getZ() + 0.5D);
                    crystal.setShowBottom(false);
                    level.addFreshEntity(crystal);
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, pos1);
                    event.setUseItem(TriState.FALSE);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                    return;
                }
            }
            event.setCancellationResult(InteractionResult.PASS);
        }
    }
}