package net.zestyblaze.lycanthropy.client.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.zestyblaze.lycanthropy.client.model.WerewolfEntityModel;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WerewolfEntityRenderer extends GeoEntityRenderer<WerewolfEntity> {
    public WerewolfEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WerewolfEntityModel());
    }

    @Override
    public RenderLayer getRenderType(WerewolfEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("rightItem") && !mainHand.isEmpty()) {
            if(!MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                stack.push();
                stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-75));
                stack.translate(0.3D, -0.2D, 1.1D);
                stack.scale(1.0f, 1.0f, 1.0f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, rtb,0);
                stack.pop();
                bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
            }
        }else if (bone.getName().equals("leftItem") && !offHand.isEmpty()) {
            if (!MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                stack.push();
                stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-75));
                stack.translate(-0.3D, -0.2D, 1.1D);
                stack.scale(1.0f, 1.0f, 1.0f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(offHand, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, rtb, 0);
                stack.pop();
                bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
            }
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
