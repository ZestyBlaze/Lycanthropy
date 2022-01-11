package net.zestyblaze.lycanthropy.common.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.block.blockentity.BearTrapBlockEntity;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyBlockInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyDamageSources;
import org.jetbrains.annotations.Nullable;

public class BearTrapBlock extends BlockWithEntity {
    public static final BooleanProperty CLOSED;
    protected static final VoxelShape OPEN_SHAPE;
    protected static final VoxelShape CLOSED_SHAPE;

    public BearTrapBlock(Settings settings) {
        super(settings.nonOpaque().noCollision());
        this.setDefaultState((this.stateManager.getDefaultState()).with(CLOSED, true));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean bl = !(Boolean)state.get(CLOSED);
        return bl ? OPEN_SHAPE : CLOSED_SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BearTrapBlockEntity(pos,state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()){
            if(world.getBlockEntity(pos) instanceof BearTrapBlockEntity bearTrapBlockEntity && world.getBlockState(pos).get(CLOSED)){
                bearTrapBlockEntity.increaseWinder(1);
                world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.7F,1.5F);
                bearTrapBlockEntity.sync();
                if(bearTrapBlockEntity.winder >= 40) {
                    world.setBlockState(pos, state.with(CLOSED, false), Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
                    bearTrapBlockEntity.setWinder(0);
                }
            }

        }
        return ActionResult.PASS;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity user) {
            if (!world.isClient) {
                if(state.get(CLOSED)){
                    entity.slowMovement(state, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));//TODO make Snared status effect instead
                }else{
                    if(this == LycanthropyBlockInit.SILVER_BEAR_TRAP_BLOCK){
                        user.damage(LycanthropyDamageSources.SILVER, LycanthropyComponentInit.WEREWOLF.get(user).getIsWerewolf() ? 5.0F : 1.0F);
                    }else{
                        user.damage(DamageSource.CACTUS, 1.0F);
                    }
                    world.setBlockState(pos, state.with(CLOSED, true), 3);
                }

            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLOSED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    static {
        CLOSED = BooleanProperty.of("closed");
        CLOSED_SHAPE = Block.createCuboidShape(2,0,2, 14, 3, 14);
        OPEN_SHAPE = Block.createCuboidShape(2,0,2, 14, 3, 14);
    }
}
