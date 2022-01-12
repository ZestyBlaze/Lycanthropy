package net.zestyblaze.lycanthropy.common.block.blockentity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.block.BearTrapBlock;
import net.zestyblaze.lycanthropy.common.block.CageBlock;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyBlockEntityInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static net.zestyblaze.lycanthropy.common.block.BearTrapBlock.CLOSED;

public class BearTrapBlockEntity extends BlockEntity implements IAnimatable, Inventory {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    AnimationFactory factory = new AnimationFactory(this);
    public int winder = 0;

    public BearTrapBlockEntity( BlockPos pos, BlockState state) {
        super(LycanthropyBlockEntityInit.BEAR_TRAP_BLOCK_ENTITY, pos, state);
    }


    public <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(event.getAnimatable().getWorld().getBlockState(event.getAnimatable().getPos()).getBlock() instanceof BearTrapBlock) {
            if(event.getAnimatable().getWorld().getBlockState(event.getAnimatable().getPos()).get(CLOSED) && winder > 0){
                builder.addAnimation("animation.bear_trap.opening", true);
            }else if(event.getAnimatable().getWorld().getBlockState(event.getAnimatable().getPos()).get(CLOSED) && winder==0){
                builder.addAnimation("animation.bear_trap.closed", true);
            }else if(!event.getAnimatable().getWorld().getBlockState(event.getAnimatable().getPos()).get(CLOSED)){
                builder.addAnimation("animation.bear_trap.open", true);
            }
            event.getController().setAnimation(builder);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controllerName", 4, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public int getWinder() {
        return winder;
    }

    public void setWinder(int value) {
            this.winder = value;
    }
    public void increaseWinder(int value){
        if(getWinder()<90){
            setWinder(getWinder()+value);
        }

    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        writeNbt(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this, (BlockEntity b) -> this.toNbt());
    }



    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("Winder")) {
            setWinder(nbt.getInt("Winder"));
        }
        Inventories.readNbt(nbt, inventory);
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Winder", getWinder());
        Inventories.writeNbt(nbt, inventory);
    }
    public NbtCompound toNbt(){
        NbtCompound rtn = new NbtCompound();
        rtn.putInt("Winder", getWinder());
        Inventories.writeNbt(rtn, inventory);
        return rtn;
    }


    public void sync() {
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
            toUpdatePacket();
        }
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            if (getStack(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
