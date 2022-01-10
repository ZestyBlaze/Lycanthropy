package net.zestyblaze.lycanthropy.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "bobView", at = @At(value = "INVOKE", ordinal = 1,target = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"), cancellable = true)
    private void cancelBob(MatrixStack matrices, float f, CallbackInfo ci){
        PlayerEntity playerEntity = (PlayerEntity)this.client.getCameraEntity();
        LycanthropyComponentInit.WEREWOLF.maybeGet(playerEntity).ifPresent(lycanthropyPlayerComponent -> {
            if(playerEntity.isSprinting()){
                ci.cancel();
            }
        });
    }

}
