package net.zestyblaze.lycanthropy.common.registry;

import net.minecraft.entity.damage.DamageSource;

public class LycanthropyDamageSources {
    public static final DamageSource SILVER = new LycanthropyDamageSource("silver");
    public static final DamageSource FAILED_RITUAL = new LycanthropyDamageSource("failed_ritual").setBypassesArmor();

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
