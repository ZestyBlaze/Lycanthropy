package net.zestyblaze.lycanthropy.common.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.statuseffect.BeastSenseEffect;
import net.zestyblaze.lycanthropy.common.statuseffect.LycanthropyStatusEffect;

import java.util.LinkedHashMap;
import java.util.Map;

public class LycanthropyStatusEffectsInit {
    private static final Map<StatusEffect, Identifier> STATUS_EFFECTS = new LinkedHashMap<>();

    public static final StatusEffect BLEEDING = register("bleeding", new LycanthropyStatusEffect(StatusEffectCategory.HARMFUL, 0xF50033));
    public static final StatusEffect BEAST_SENSE = register("beast_sense", new BeastSenseEffect(StatusEffectCategory.BENEFICIAL, 0x807c50));
    public static final StatusEffect WEREWOLF_VISION = register("werewolf_vision", new LycanthropyStatusEffect(StatusEffectCategory.BENEFICIAL, 0x807c50));
    public static final StatusEffect SNARED = register("snared", new LycanthropyStatusEffect(StatusEffectCategory.HARMFUL, 0x515151));

    private static <T extends StatusEffect> T register(String name, T effect) {
        STATUS_EFFECTS.put(effect, new Identifier(Lycanthropy.MODID, name));
        return effect;
    }

    public static void registerStatusEffects() {
        STATUS_EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, STATUS_EFFECTS.get(effect), effect));

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Status Effects Registered");
        }
    }
}
