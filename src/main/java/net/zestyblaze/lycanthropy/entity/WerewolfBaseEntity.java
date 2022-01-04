package net.zestyblaze.lycanthropy.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public abstract class WerewolfBaseEntity extends HostileEntity {
    public WerewolfBaseEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
}
