package com.jdolphin.aabeacon.common.entity;

import com.jdolphin.aabeacon.common.AABHelper;
import com.jdolphin.aabeacon.common.init.AABEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class LaserCrystal extends EndCrystal {
    private static final EntityDataAccessor<Vector3f> DATA_BEAM_TARGET = SynchedEntityData.defineId(LaserCrystal.class, EntityDataSerializers.VECTOR3);;
    protected int attackTime = 0;
    public Entity target;

    public LaserCrystal(EntityType<? extends EndCrystal> entityType, Level level) {
        super(entityType, level);
    }

    public LaserCrystal(Level level) {
        super(AABEntities.CRYSTAL, level);
    }

    public boolean showsBottom() {
        return false;
    }

    private void onDestroyedBy(DamageSource source) {}

    public void tick() {
        ++this.time;
        if (this.level() instanceof ServerLevel serverLevel) {
            BlockPos blockpos = this.blockPosition();
            if (serverLevel.getBlockState(blockpos.below()).is(Blocks.BEACON)) {
                int lvl = updateBase(serverLevel, blockpos.below());
                float radius = 0;

                if (lvl == 1) radius = 10;
                if (lvl == 2) radius = 15;
                if (lvl == 3) radius = 20;
                if (lvl == 4) radius = 25;
                List<Entity> entityList = getEntitiesNearby(this, radius);
                if (entityList != null && !entityList.isEmpty()) {
                    this.target = entityList.get(0);
                    Vec3 pos = target.position();
                    setTarget(new Vector3f((float) pos.x, (float) pos.y, (float) pos.z));
                } else {
                    target = null;
                    setTarget(new Vector3f());
                    attackTime = 0;
                }
                if (target != null) {
                    attackTime++;
                    if (attackTime >= 20) {
                        target.hurt(this.damageSources().magic(), lvl);
                        attackTime = -10;
                    }
                }
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_BEAM_TARGET, new Vector3f());
    }

    public void setTarget(Vector3f beamTarget) {
        this.getEntityData().set(DATA_BEAM_TARGET, beamTarget);
    }

    public Vector3f getTarget() {
        return this.getEntityData().get(DATA_BEAM_TARGET);
    }

    public static List<Entity> getEntitiesNearby(Entity entity, double range) {
        if (!entity.level().isClientSide()) {
            AABB boundingBox = entity.getBoundingBox().inflate(range);
            List<Entity> entities = entity.level().getEntitiesOfClass(Entity.class, boundingBox);
            entities.remove(entity);
            entities.removeIf(e -> {
                String entityAsString = AABHelper.getEntityAsString(e.getType());
                return !AABHelper.getAllowed().contains(entityAsString);
            });
            return entities;
        }
        return null;
    }

    private static int updateBase(Level level, BlockPos pos) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();

        int i = 0;

        for(int j = 1; j <= 4; i = j++) {
            int k = y - j;
            if (k < level.getMinBuildHeight()) {
                break;
            }

            boolean flag = true;

            for(int l = x - j; l <= x + j && flag; ++l) {
                for(int i1 = z - j; i1 <= z + j; ++i1) {
                    if (!level.getBlockState(new BlockPos(l, k, i1)).is(BlockTags.BEACON_BASE_BLOCKS)) {
                        flag = false;
                        break;
                    }
                }
            }

            if (!flag) {
                break;
            }
        }

        return i;
    }

    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    protected void readAdditionalSaveData(CompoundTag compound) {

    }
}
