package com.jdolphin.aabeacon.common;

import com.jdolphin.aabeacon.common.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.List;

public class AABHelper {

    public static ResourceLocation id(String s) {
        return ResourceLocation.tryBuild(Constants.MOD_ID, s);
    }

    public static List<String> defaultWhitelist() {
        return List.of("minecraft:phantom",
                "minecraft:ghast", "minecraft:blaze",
                "minecraft:vex", "minecraft:wither");
    }

    public static List<? extends String> getAllowed() {
        return Services.PLATFORM.allowedTargets();
    }

    public static String getEntityAsString(EntityType<?> type) {
        ResourceLocation rl = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        return rl.toString();
    }
}
