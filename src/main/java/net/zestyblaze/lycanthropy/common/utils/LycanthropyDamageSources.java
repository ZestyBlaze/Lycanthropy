package net.zestyblaze.lycanthropy.common.utils;

import net.minecraft.entity.damage.DamageSource;

public class LycanthropyDamageSources {
    public static final DamageSource SILVER = new LycanthropyDamageSource("silver");
    public static final DamageSource FAILED_RITUAL = new LycanthropyDamageSource("failed_ritual").setBypassesArmor();
    public static final DamageSource BEAR_TRAP = new LycanthropyDamageSource("bear_trap");

    private static class LycanthropyDamageSource extends DamageSource {
        public LycanthropyDamageSource(String name) {
            super(name);
        }

        private boolean bypassesArmor;
        private float exhaustion = 0.1F;

        @Override
        public DamageSource setBypassesArmor() {
            this.bypassesArmor = true;
            this.exhaustion = 0.0F;
            return this;
        }
    }
}
