package com.jdolphin.aabeacon.common.init;

import com.jdolphin.aabeacon.common.AABHelper;
import com.jdolphin.aabeacon.common.entity.LaserCrystal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class AABEntities {
    private static final Map<ResourceLocation, EntityType<?>> ALL = new HashMap<>();

    public static final EntityType<LaserCrystal> CRYSTAL = register("laser_crystal",
            EntityType.Builder.<LaserCrystal>of((LaserCrystal::new), MobCategory.MISC).sized(2.0f, 2.0f)
                    .clientTrackingRange(16).updateInterval(Integer.MAX_VALUE));

    private static <E extends Entity> EntityType<E> register(String name, EntityType.Builder<E> builder) {
        EntityType<E> type = builder.build(name);
        ALL.put(AABHelper.id(name), type);
        return type;
    }

    public static void init(BiConsumer<EntityType<?>, ResourceLocation> r) {
        for (var e : ALL.entrySet()) {
            r.accept(e.getValue(), e.getKey());
        }
    }
}
