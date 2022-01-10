package net.zestyblaze.lycanthropy.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyDamageSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LycanthropyDamageSource {

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void lycanthropeBlockDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LycanthropyComponentInit.WEREWOLF.maybeGet(this).ifPresent(werewolfComponent -> {
            if(werewolfComponent.getIsWerewolf()) {
                if(source != DamageSource.DROWN || source != DamageSource.OUT_OF_WORLD || source != DamageSource.CRAMMING || source != DamageSource.MAGIC || source != LycanthropyDamageSources.SILVER) {
                    //cir.setReturnValue(false); //TODO enable this, but add balance first
                }
            }
        });
    }
}
