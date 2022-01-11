package net.zestyblaze.lycanthropy.client.renderer;

import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.client.model.BearTrapBlockModel;
import net.zestyblaze.lycanthropy.client.model.CageBlockModel;
import net.zestyblaze.lycanthropy.common.block.blockentity.BearTrapBlockEntity;
import net.zestyblaze.lycanthropy.common.block.blockentity.CageBlockEntity;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import static net.zestyblaze.lycanthropy.common.block.CageBlock.HALF;

public class BearTrapBlockRenderer extends GeoBlockRenderer<BearTrapBlockEntity> {
    public BearTrapBlockRenderer() {
        super(new BearTrapBlockModel());
    }

    @Override
    public RenderLayer getRenderType(BearTrapBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

}
