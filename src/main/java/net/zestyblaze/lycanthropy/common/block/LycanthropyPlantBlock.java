package net.zestyblaze.lycanthropy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyBlockInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.utils.LycanthropyDamageSources;

@SuppressWarnings("deprecation")
public class LycanthropyPlantBlock extends PlantBlock {
    public LycanthropyPlantBlock(Settings settings) {
        super(settings.noCollision());
    }
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if(this == LycanthropyBlockInit.WOLFSBANE && entity instanceof PlayerEntity player && LycanthropyComponentInit.WEREWOLF.get(player).getIsWerewolf()){
            player.damage(LycanthropyDamageSources.WOLFSBANE,  5.0F);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}