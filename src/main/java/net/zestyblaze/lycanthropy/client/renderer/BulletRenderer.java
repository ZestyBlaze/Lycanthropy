package net.zestyblaze.lycanthropy.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.zestyblaze.lycanthropy.client.model.BulletModel;
import net.zestyblaze.lycanthropy.common.entity.SilverBulletEntity;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BulletRenderer extends GeoProjectilesRenderer<SilverBulletEntity> {
    public BulletRenderer(EntityRendererFactory.Context renderManagerIn) {
        super(renderManagerIn, new BulletModel());
    }

    protected int getBlockLight(SilverBulletEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public RenderLayer getRenderType(SilverBulletEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(SilverBulletEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
        red, green, blue, partialTicks);
        stackIn.scale(animatable.age > 2 ? 0.5F : 0.0F, animatable.age > 2 ? 0.5F : 0.0F,
        animatable.age > 2 ? 0.5F : 0.0F);
    }
}