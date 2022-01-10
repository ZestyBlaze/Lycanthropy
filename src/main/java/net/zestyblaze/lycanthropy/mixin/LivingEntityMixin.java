package net.zestyblaze.lycanthropy.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyStatusEffectsInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource source, CallbackInfoReturnable<Boolean> callbackInfo) {
        if ((Object) this instanceof PlayerEntity player && LycanthropyComponentInit.WEREWOLF.get(player).getIsWerewolf() && fallDistance <= 5) {
            callbackInfo.setReturnValue(false);
        }
    }

    @Inject(method = "getJumpVelocity", at = @At("RETURN"), cancellable = true)
    private void getJumpVelocity(CallbackInfoReturnable<Float> callbackInfo) {
        if ((Object) this instanceof PlayerEntity player && LycanthropyComponentInit.WEREWOLF.get(player).getIsWerewolf()) {
            if(player.isSprinting()){
                callbackInfo.setReturnValue(callbackInfo.getReturnValue() * 1.6f);
            }
            callbackInfo.setReturnValue(callbackInfo.getReturnValue() * 1.2f);
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"), ordinal = 0, argsOnly = true)
    private float modifyDamage(float amount, DamageSource source) {
        if (!world.isClient) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;
            if(livingEntity.hasStatusEffect(LycanthropyStatusEffectsInit.BLEEDING)){
                amount = amount*1.5F;
            }
        }
        return amount;
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void canFallHurt(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        LycanthropyComponentInit.WEREWOLF.maybeGet(this).ifPresent(lycanthropyPlayerComponent -> {
            if(fallDistance <= 6){
                cir.setReturnValue(false);
            }
        });
    }
}
