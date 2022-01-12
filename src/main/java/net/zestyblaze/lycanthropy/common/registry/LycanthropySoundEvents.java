package net.zestyblaze.lycanthropy.common.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;

import java.util.LinkedHashMap;
import java.util.Map;

public class LycanthropySoundEvents {
    private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();

    public static final SoundEvent ENTITY_WEREWOLF_AMBIENT = register("entity.werewolf.ambient");
    public static final SoundEvent ENTITY_WEREWOLF_HOWL = register("entity.werewolf.howl");
    public static final SoundEvent ENTITY_WEREWOLF_HURT = register("entity.werewolf.hurt");
    public static final SoundEvent ENTITY_WEREWOLF_DEATH = register("entity.werewolf.death");

    private static SoundEvent register(String name) {
        SoundEvent soundEvent = new SoundEvent(new Identifier(Lycanthropy.MODID, name));
        SOUND_EVENTS.put(soundEvent, new Identifier(Lycanthropy.MODID, name));
        return soundEvent;
    }

    public static void registerSoundEvents() {
        SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent));
    }

}
