package com.chikoritalover.terrarium.client.render;

import com.chikoritalover.terrarium.entity.BoomerangEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value = EnvType.CLIENT)
public class BoomerangEntityRenderer<T extends Entity> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public BoomerangEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        assert entity instanceof BoomerangEntity;

        matrices.push();
        matrices.translate(0.0, 0.1, 0.0);
        float f = entity.prevYaw * MathHelper.RADIANS_PER_DEGREE;
        float g = entity.getYaw() * MathHelper.RADIANS_PER_DEGREE;
        double d = MathHelper.lerp(tickDelta, Math.sin(f), Math.sin(g));
        double e = MathHelper.lerp(tickDelta, Math.cos(f), Math.cos(g));
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) (Math.atan2(d, e) + MathHelper.HALF_PI + Math.PI / 36.0)));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(((BoomerangEntity) entity).getRotation(tickDelta)));

        this.itemRenderer.renderItem(((BoomerangEntity) entity).asItemStack(), ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getId());
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
