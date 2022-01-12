package net.zestyblaze.lycanthropy.common.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.block.blockentity.BearTrapBlockEntity;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyBlockInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyDamageSources;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyTags;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

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
        if (!world.isClient) {
            if(world.getBlockEntity(pos) instanceof BearTrapBlockEntity bearTrapBlockEntity){
                if(player.getStackInHand(hand).isEmpty()){
                    if(!world.getBlockState(pos).get(CLOSED)){
                        if(player.isSneaking()){
                            if(!bearTrapBlockEntity.getStack(0).isEmpty()){
                                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), bearTrapBlockEntity.getStack(0));
                                bearTrapBlockEntity.setStack(0, new ItemStack(Items.AIR));
                                bearTrapBlockEntity.sync();
                            }else if(!bearTrapBlockEntity.getStack(1).isEmpty()){
                                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), bearTrapBlockEntity.getStack(1));
                                bearTrapBlockEntity.setStack(1, new ItemStack(Items.AIR));
                                bearTrapBlockEntity.sync();
                            }
                        }
                    }else {
                        bearTrapBlockEntity.increaseWinder(1);
                        bearTrapBlockEntity.sync();
                        world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.7F,1.5F);
                        if(bearTrapBlockEntity.winder >= 40) {
                            world.setBlockState(pos, state.with(CLOSED, false), Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
                            bearTrapBlockEntity.setWinder(0);
                        }
                    }
                }else if(ItemTags.LEAVES.contains(player.getStackInHand(hand).getItem()) && bearTrapBlockEntity.getStack(0).isEmpty()){
                    ItemStack stack = player.getStackInHand(hand);
                    bearTrapBlockEntity.setStack(0, stack.split(1));
                    bearTrapBlockEntity.sync();
                }else if(LycanthropyTags.MEAT.contains(player.getStackInHand(hand).getItem()) && bearTrapBlockEntity.getStack(1).isEmpty()){
                    ItemStack stack = player.getStackInHand(hand);
                    bearTrapBlockEntity.setStack(1, stack.split(1));
                    bearTrapBlockEntity.sync();
                }
            }

        }
        return ActionResult.success(world.isClient);
    }



    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity user) {
            if(state.get(CLOSED)){
                entity.slowMovement(state, new Vec3d(0.2D, 0.25D, 0.2D));//TODO make Snared status effect instead
            }
            if (!world.isClient) {
                if(!state.get(CLOSED)){
                    if(this == LycanthropyBlockInit.SILVER_BEAR_TRAP_BLOCK){
                        user.damage(LycanthropyDamageSources.SILVER, LycanthropyComponentInit.WEREWOLF.get(user).getIsWerewolf() ? 5.0F : 1.0F);
                    }else{
                        user.damage(DamageSource.CACTUS, 1.0F);
                    }
                    world.createAndScheduleBlockTick(pos,this,60);
                    world.setBlockState(pos, state.with(CLOSED, true), 3);
                }

            }
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(CLOSED)) {
            if(world.getBlockEntity(pos) instanceof BearTrapBlockEntity bearTrapBlockEntity){
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), bearTrapBlockEntity.getStack(0));
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), bearTrapBlockEntity.getStack(1));
            }
            world.breakBlock(pos, true);
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

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BearTrapBlockEntity blockEntity = (BearTrapBlockEntity) world.getBlockEntity(pos);
        for(int i = 0; i < 2; i++){
            dropStack(world, pos, blockEntity.getStack(i));
            blockEntity.setStack(i, ItemStack.EMPTY);
        }
    }

    static {
        CLOSED = BooleanProperty.of("closed");
        CLOSED_SHAPE = Block.createCuboidShape(2,0,2, 14, 5, 14);
        OPEN_SHAPE = Block.createCuboidShape(2,0,2, 14, 5, 14);
    }
}