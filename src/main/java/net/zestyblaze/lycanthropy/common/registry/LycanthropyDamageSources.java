package net.zestyblaze.lycanthropy.common.registry;

import net.minecraft.entity.damage.DamageSource;

public class LycanthropyDamageSources {
    public static final DamageSource SILVER = new LycanthopyuDamageSource("silver");

    private static class LycanthopyuDamageSource extends DamageSource {
        public LycanthopyuDamageSource(String name) {
            super(name);
        }
    }
}
