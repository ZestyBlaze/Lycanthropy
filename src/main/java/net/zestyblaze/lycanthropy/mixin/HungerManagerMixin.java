package net.zestyblaze.lycanthropy.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    //Effectively disable vanilla hunger system while werewolf form active
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void disableUpdate(PlayerEntity player, CallbackInfo ci){
        if(LycanthropyComponentInit.WEREWOLF.get(player).getIsWerewolf()){
            ci.cancel();
        }
    }
}
