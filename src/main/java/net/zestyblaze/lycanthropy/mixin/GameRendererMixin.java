package net.zestyblaze.lycanthropy.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Program;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceManager;
import net.zestyblaze.lycanthropy.client.LycanthropyShader;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "bobView", at = @At(value = "INVOKE", ordinal = 1,target = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"), cancellable = true)
    private void cancelBob(MatrixStack matrices, float f, CallbackInfo ci){
        PlayerEntity playerEntity = (PlayerEntity)this.client.getCameraEntity();
        LycanthropyComponentInit.WEREWOLF.maybeGet(playerEntity).ifPresent(lycanthropyPlayerComponent -> {
            assert playerEntity != null;
            if(playerEntity.isSprinting()){
                ci.cancel();
            }
        });
    }
    @Inject(method = "loadShaders", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/google/common/collect/Lists;newArrayListWithCapacity(I)Ljava/util/ArrayList;", remap = false), locals = LocalCapture.CAPTURE_FAILHARD)
    private void loadShaders(ResourceManager resourceManager, CallbackInfo ci, List<Program> _programsToClose, List<Pair<Shader, Consumer<Shader>>> shadersToLoad)
    throws IOException {
        LycanthropyShader.init(resourceManager, shadersToLoad);
    }
}
