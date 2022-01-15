package net.zestyblaze.lycanthropy.client;

import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.mixin.RenderLayerAccessor;

import java.util.function.Function;


public class LycanthropyRenderLayer extends RenderLayer {
    public LycanthropyRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
    private static RenderLayer makeLayer(String name, VertexFormat format, VertexFormat.DrawMode mode, int bufSize, boolean hasCrumbling, boolean sortOnUpload, RenderLayer.MultiPhaseParameters glState) {
        return RenderLayerAccessor.of(name, format, mode, bufSize, hasCrumbling, sortOnUpload, glState);
    }


    private static final Function<Identifier, RenderLayer> WEREWOLF_VISION = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters glState = RenderLayer.MultiPhaseParameters.builder()
        .shader(new RenderPhase.Shader(LycanthropyShader::werewolfVision))
        .texture(new RenderPhase.Texture(texture, false, false))
        .transparency(TRANSLUCENT_TRANSPARENCY)
        .cull(DISABLE_CULLING)
        .lightmap(RenderLayer.ENABLE_LIGHTMAP)
        .overlay(RenderLayer.ENABLE_OVERLAY_COLOR)
        .depthTest(ALWAYS_DEPTH_TEST)
        .build(true);
        return makeLayer(Lycanthropy.MODID + "werewolfVision", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, glState);
    });

    public static RenderLayer getWerewolfVisionLayer(Identifier texture) {
        return WEREWOLF_VISION.apply(texture);
    }

}
