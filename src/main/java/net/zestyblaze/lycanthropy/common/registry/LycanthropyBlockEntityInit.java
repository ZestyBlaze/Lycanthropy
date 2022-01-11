package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.block.blockentity.BearTrapBlockEntity;
import net.zestyblaze.lycanthropy.common.block.blockentity.CageBlockEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class LycanthropyBlockEntityInit {
    public static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();


    public static final BlockEntityType<CageBlockEntity> CAGE_BLOCK_ENTITY = register("cage_block_entity", FabricBlockEntityTypeBuilder.create(CageBlockEntity::new, LycanthropyBlockInit.CAGE_BLOCK).build(null));
    public static final BlockEntityType<BearTrapBlockEntity> BEAR_TRAP_BLOCK_ENTITY = register("bear_trap_block_entity", FabricBlockEntityTypeBuilder.create(BearTrapBlockEntity::new, LycanthropyBlockInit.BEAR_TRAP_BLOCK, LycanthropyBlockInit.SILVER_BEAR_TRAP_BLOCK).build(null));

    /**
     *
     * @param name
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(Lycanthropy.MODID, name));
        return type;
    }

    public static void registerBlockEntities(){
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }
}
