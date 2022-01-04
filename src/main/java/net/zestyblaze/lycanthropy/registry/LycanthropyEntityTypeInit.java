package net.zestyblaze.lycanthropy.registry;

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
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class LycanthropyEntityTypeInit {
    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static final EntityType<WerewolfEntity> WEREWOLF = create("werewolf", WerewolfEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WerewolfEntity::new).dimensions(EntityDimensions.fixed(0.8f, 2.8f)).build());

    private static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.put(type, new Identifier(Lycanthropy.MODID, name));
        return type;
    }

    public static void initEntityTypes() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
    }
}
