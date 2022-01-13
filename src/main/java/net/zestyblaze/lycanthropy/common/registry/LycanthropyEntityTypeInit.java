package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.entity.HunterEntity;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class LycanthropyEntityTypeInit {
    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static final EntityType<WerewolfEntity> WEREWOLF = register("werewolf", WerewolfEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WerewolfEntity::new).dimensions(EntityDimensions.fixed(1f, 3f)).build());
    public static final EntityType<HunterEntity> HUNTER_VILLAGER = register("hunter_villager", HunterEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HunterEntity::new).dimensions(EntityDimensions.fixed(0.6f,1.8f)).build());

    private static <T extends LivingEntity> EntityType<T> register(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.put(type, new Identifier(Lycanthropy.MODID, name));
        return type;
    }

    public static void initEntityTypes() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Entities Registered");
        }
    }
}
