package net.zestyblaze.lycanthropy.client.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class WerewolfEyesFeatureRenderer extends GeoLayerRenderer<WerewolfEntity> {
    private static final Identifier LAYER = new Identifier(Lycanthropy.MODID, "textures/entity/werewolf/werewolf_eyes.png");
    private static final Identifier MODEL = new Identifier(Lycanthropy.MODID, "geo/werewolf.geo.json");
    private final IGeoRenderer<WerewolfEntity> renderer;
    public WerewolfEyesFeatureRenderer(WerewolfEntityRenderer entityRendererIn, WerewolfEyesRenderer renderer) {
        super(entityRendererIn);
        this.renderer = renderer;
    }
    public static class WerewolfEyesRenderer extends GeoEntityRenderer<WerewolfEntity> {
        public WerewolfEyesRenderer(EntityRendererFactory.Context ctx, AnimatedGeoModel<WerewolfEntity> modelProvider) {
            super(ctx, modelProvider);
        }
    }
    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, WerewolfEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(true){//TODO add condition to activate glowing eyes
            renderer.render(getEntityModel().getModel(MODEL),
            entitylivingbaseIn,
            partialTicks,
            RenderLayer.getEntityTranslucent(LAYER),
            matrixStackIn,
            bufferIn,
            bufferIn.getBuffer(RenderLayer.getEyes(LAYER)),
            0xF000F0,
            OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
