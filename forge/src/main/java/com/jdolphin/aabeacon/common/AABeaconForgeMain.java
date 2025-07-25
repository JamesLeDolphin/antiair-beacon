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
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class AABeaconForgeMain {
    
    public AABeaconForgeMain() {
        AABMain.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bind(bus, Registries.ENTITY_TYPE, AABEntities::init);
        MinecraftForge.EVENT_BUS.register(new Event());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AABCommonConfig.SPEC, "aabeacon-common.toml");
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
                    event.setUseItem(net.minecraftforge.eventbus.api.Event.Result.ALLOW);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                    return;
                }
            }
            event.setCancellationResult(InteractionResult.PASS);
        }
    }
}