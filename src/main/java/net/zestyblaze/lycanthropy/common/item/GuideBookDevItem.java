package net.zestyblaze.lycanthropy.common.item;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import javax.annotation.Nonnull;
import java.util.List;

public class GuideBookDevItem extends Item implements IAnimatable, ISyncable {
    public final AnimationFactory factory = new AnimationFactory(this);
    public boolean holdsItem = false;
    private static final int ANIM_OPEN = 0;
    public  boolean bl1 = true;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity player && !world.isClient ){
            this.holdsItem = selected;
            /*
            if(selected && bl1){
                final int id = GeckoLibUtil.guaranteeIDForStack(player.getMainHandStack(), (ServerWorld) world);
                GeckoLibNetwork.syncAnimation(player, this, id, ANIM_OPEN);
                for (PlayerEntity otherPlayer : PlayerLookup.tracking(player)) {
                    GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
                }
                bl1 = false;
            }else if(!selected && !bl1){
                final int id = GeckoLibUtil.guaranteeIDForStack(player.getMainHandStack(), (ServerWorld) world);
                GeckoLibNetwork.syncAnimation(player, this, id, ANIM_OPEN);
                for (PlayerEntity otherPlayer : PlayerLookup.tracking(player)) {
                    GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
                }
                bl1=true;
            }

             */
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public GuideBookDevItem(Settings settings) {
        super(settings);
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(!FabricLoader.getInstance().isModLoaded("patchouli")) {
            tooltip.add(new TranslatableText("text.lycanthropy.guide_book.fail").formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }


    @Nonnull
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof ServerPlayerEntity player) {
            Book book = BookRegistry.INSTANCE.books.get(Registry.ITEM.getId(this));
            PatchouliAPI.get().openBookGUI(player, book.id);
            user.playSound(PatchouliSounds.getSound(book.openSound, PatchouliSounds.BOOK_OPEN), 1, (float) (0.7 + Math.random() * 0.4));
        }
        return super.use(world, user, hand);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "0", 0, this::predicate));
    }


    private <E extends Item & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(holdsItem){
            builder.addAnimation("animation.guide_book.opening", false);
            builder.addAnimation("animation.guide_book.open", true);
        }else{
            builder.addAnimation("animation.guide_book.closing", false);
            builder.addAnimation("animation.guide_book.closed", true);
        }
        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        /*
        if (state == ANIM_OPEN) {
            @SuppressWarnings("rawtypes")
            final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, "0");
            AnimationBuilder builder = new AnimationBuilder();
            if(holdsItem){
                builder.addAnimation("animation.guide_book.opening", false);
                builder.addAnimation("animation.guide_book.open", true);
            }else{
                builder.addAnimation("animation.guide_book.closing", false);
                builder.addAnimation("animation.guide_book.closed", true);
            }

            controller.markNeedsReload();
            controller.setAnimation(builder);
        }

         */

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

}
