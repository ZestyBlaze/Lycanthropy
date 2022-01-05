package net.zestyblaze.lycanthropy.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource source, CallbackInfoReturnable<Boolean> callbackInfo) {
        if ((Object) this instanceof PlayerEntity player && LycanthropyComponentInit.WEREWOLF.get(player).isWerewolf() && fallDistance <= 5) {
            callbackInfo.setReturnValue(false);
        }
    }

    @Inject(method = "getJumpVelocity", at = @At("RETURN"), cancellable = true)
    private void getJumpVelocity(CallbackInfoReturnable<Float> callbackInfo) {
        if ((Object) this instanceof PlayerEntity player && LycanthropyComponentInit.WEREWOLF.get(player).isWerewolf()) {
            callbackInfo.setReturnValue(callbackInfo.getReturnValue() * 1.2f);
        }
    }
}
