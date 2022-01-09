package net.zestyblaze.lycanthropy.common.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.zestyblaze.lycanthropy.common.block.blockentity.CageBlockEntity;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CageBlock extends BlockWithEntity {
    public static final DirectionProperty FACING;
    public static final BooleanProperty OPEN;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape FULL_SHAPE;

    protected static final VoxelShape TOP_SHAPE;
    protected static final VoxelShape BOTTOM_SHAPE;

    public CageBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState((((((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(OPEN, false)))).with(HALF, DoubleBlockHalf.LOWER));

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        boolean bl = !(Boolean)state.get(OPEN);
        boolean lower = state.get(HALF) == DoubleBlockHalf.LOWER;
        return switch (direction) {
            default -> bl ? lower ? VoxelShapes.union(FULL_SHAPE , BOTTOM_SHAPE) : VoxelShapes.union(FULL_SHAPE , TOP_SHAPE) : lower ? VoxelShapes.union(EAST_SHAPE , BOTTOM_SHAPE) : VoxelShapes.union(EAST_SHAPE , TOP_SHAPE);
            case SOUTH -> bl ? lower ? VoxelShapes.union(FULL_SHAPE , BOTTOM_SHAPE) : VoxelShapes.union(FULL_SHAPE , TOP_SHAPE) : lower ?  VoxelShapes.union(SOUTH_SHAPE, BOTTOM_SHAPE) : VoxelShapes.union(SOUTH_SHAPE, TOP_SHAPE);
            case WEST -> bl ? lower ? VoxelShapes.union(FULL_SHAPE , BOTTOM_SHAPE) : VoxelShapes.union(FULL_SHAPE , TOP_SHAPE) : lower ? VoxelShapes.union(WEST_SHAPE , BOTTOM_SHAPE) : VoxelShapes.union(WEST_SHAPE , TOP_SHAPE);
            case NORTH -> bl ? lower ? VoxelShapes.union(FULL_SHAPE , BOTTOM_SHAPE) : VoxelShapes.union(FULL_SHAPE , TOP_SHAPE) : lower ? VoxelShapes.union(NORTH_SHAPE , BOTTOM_SHAPE) : VoxelShapes.union(NORTH_SHAPE , TOP_SHAPE);
        };
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf ? (((state.with(FACING, neighborState.get(FACING))).with(OPEN, neighborState.get(OPEN)))) : Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        state = state.cycle(OPEN);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
        return ActionResult.success(world.isClient);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
            boolean bl = world.isReceivingRedstonePower(blockPos) || world.isReceivingRedstonePower(blockPos.up());
            return ((((this.getDefaultState().with(FACING, ctx.getPlayerFacing())))).with(OPEN, bl)).with(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (!this.getDefaultState().isOf(block)) {
            if (bl != state.get(OPEN)) {
                world.emitGameEvent(bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            }
            world.setBlockState(pos, state.with(OPEN, bl), Block.NOTIFY_LISTENERS);
        }

    }


    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CageBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        OPEN = Properties.OPEN;
        HALF = Properties.DOUBLE_BLOCK_HALF;
        NORTH_SHAPE = VoxelShapes.union(createCuboidShape(0,0,-1,16,16,1), createCuboidShape(16, 0, 0, 17, 16,16), createCuboidShape(0, 0, 0, 1, 16,16));
        SOUTH_SHAPE = VoxelShapes.union(createCuboidShape(16, 0, 0, 17, 16,16), createCuboidShape(0, 0, 0, 1, 16,16), createCuboidShape(0,0,16, 16,16,17));
        WEST_SHAPE  = VoxelShapes.union(createCuboidShape(0,0,-1,16,16,1), createCuboidShape(0, 0, 0, 1, 16,16), createCuboidShape(0,0,16, 16,16,17));
        EAST_SHAPE  = VoxelShapes.union(createCuboidShape(0,0,-1,16,16,1), createCuboidShape(16, 0, 0, 17, 16,16), createCuboidShape(0,0,16, 16,16,17));
        FULL_SHAPE  = VoxelShapes.union(createCuboidShape(0,0,-1,16,16,1), createCuboidShape(16, 0, 0, 17, 16,16), createCuboidShape(0, 0, 0, 1, 16,16), createCuboidShape(0,0,16, 16,16,17));

        BOTTOM_SHAPE = Block.createCuboidShape(0,-1,0, 16, 0, 16);
        TOP_SHAPE = Block.createCuboidShape(0,16,-1, 17, 17, 17);

    }
}
