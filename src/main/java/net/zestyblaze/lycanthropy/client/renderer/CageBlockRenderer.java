package net.zestyblaze.lycanthropy.client.renderer;

import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.client.model.CageBlockModel;
import net.zestyblaze.lycanthropy.common.block.blockentity.CageBlockEntity;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import static net.zestyblaze.lycanthropy.common.block.CageBlock.HALF;

public class CageBlockRenderer extends GeoBlockRenderer<CageBlockEntity> {
    public CageBlockRenderer() {
        super(new CageBlockModel());
    }

    @Override
    public RenderLayer getRenderType(CageBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(CageBlockEntity tile, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        if(tile != null && tile.getCachedState().get(HALF) == DoubleBlockHalf.LOWER){
            super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
        }

    }
}
