package com.jdolphin.aabeacon.client;

import com.jdolphin.aabeacon.common.AABHelper;
import com.jdolphin.aabeacon.common.entity.LaserCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class LaserCrystalRenderer extends EndCrystalRenderer {
    private static final RenderType BEAM;
    public static final ResourceLocation CRYSTAL_BEAM_LOCATION = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
    public LaserCrystalRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(EndCrystal entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity instanceof LaserCrystal crystal) {
            Vector3f empty = new Vector3f();
            if (!crystal.getTarget().equals(empty)) {
                Vector3f target = crystal.getTarget();
                float x = (float)(target.x - entity.getX());
                float y = (float)(target.y - entity.getY()) - 1;
                float z = (float)(target.z - entity.getZ());

                renderCrystalBeams(entity, x, -y + getY(crystal, partialTicks), z, partialTicks, crystal.tickCount, poseStack, buffer, packedLight);
            }
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public static float getY(EndCrystal endCrystal, float partialTick) {
        float f = (float)endCrystal.time + partialTick;
        float f1 = Mth.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = (f1 * f1 + f1) * 0.4F;
        return f1 - 1.4F;
    }

    public static void renderCrystalBeams(EndCrystal crystal, float x, float y, float z, float partialTick, int tickCount, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float f = Mth.sqrt(x * x + z * z);
        float sqrt = Mth.sqrt(x * x + y * y + z * z);
        poseStack.pushPose();
        poseStack.translate(0.0F, 2, 0.0F);
        poseStack.translate(0.0F, getY(crystal, partialTick), 0.0F);
        poseStack.mulPose(Axis.YP.rotation((float)(-Math.atan2(z, x)) + ((float)Math.PI / 2F)));
        poseStack.mulPose(Axis.XP.rotation((float)(-Math.atan2(f, y)) - ((float)Math.PI / 2F)));
        VertexConsumer vertexconsumer = buffer.getBuffer(BEAM);
        float f2 = 0.0F - ((float)tickCount + partialTick) * 0.01F;
        float f3 = sqrt / 32.0F - ((float)tickCount + partialTick) * 0.01F;

        float f4 = 0.0F;
        float f5 = 0.75F;
        float f6 = 0.0F;
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        for(int j = 1; j <= 8; ++j) {
            float f7 = Mth.sin((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
            float f8 = Mth.cos((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
            float f9 = (float)j / 8.0F;
            vertexconsumer.vertex(matrix4f, f4 * 0.2F, f5 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f6, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, f4, f5, -sqrt).color(255, 255, 255, 255).uv(f6, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, f7, f8, -sqrt).color(255, 255, 255, 255).uv(f9, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, f7 * 0.2F, f8 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f9, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            f4 = f7;
            f5 = f8;
            f6 = f9;
        }

        poseStack.popPose();
    }

    static {
        BEAM = RenderType.entitySmoothCutout(CRYSTAL_BEAM_LOCATION);
    }
}
