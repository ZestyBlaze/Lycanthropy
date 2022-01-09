package net.zestyblaze.lycanthropy.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.item.ItemStack;
import net.zestyblaze.lycanthropy.common.item.FlintlockItem;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyItemInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MouseMixin   {
    @Unique
    private int timer = 0;

    @Shadow private double cursorDeltaX;

    @Shadow private double cursorDeltaY;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SmoothUtil;clear()V", ordinal = 3))
    private void recoilHead(CallbackInfo ci){

        if(client.player.getMainHandStack().isOf(LycanthropyItemInit.FLINTLOCK_RIFLE)){
            ItemStack itemStack = client.player.getMainHandStack();
            if(itemStack.getItem() instanceof FlintlockItem flintlockItem){
                if((itemStack.getNbt() != null && itemStack.getNbt().getBoolean("Fired"))){
                    //this.cursorDeltaY -=10;
                }
            }
        }
    }

    //@Override
    public void access() {
        System.out.println("Access");
        this.cursorDeltaY -= 1;
    }
}
