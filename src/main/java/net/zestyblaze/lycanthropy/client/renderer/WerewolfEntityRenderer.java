package net.zestyblaze.lycanthropy.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.client.model.WerewolfEntityModel;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WerewolfEntityRenderer extends GeoEntityRenderer<WerewolfEntity> {
    public WerewolfEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WerewolfEntityModel());
    }

    @Override
    public RenderLayer getRenderType(WerewolfEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
