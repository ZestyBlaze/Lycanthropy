package net.zestyblaze.lycanthropy.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropySoundEvents;
import org.jetbrains.annotations.Nullable;

public abstract class WerewolfBaseEntity extends HostileEntity {
    public WerewolfBaseEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return LycanthropySoundEvents.ENTITY_WEREWOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return LycanthropySoundEvents.ENTITY_WEREWOLF_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return LycanthropySoundEvents.ENTITY_WEREWOLF_AMBIENT;
    }
}
