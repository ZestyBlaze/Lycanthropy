package net.zestyblaze.lycanthropy.common.item;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyItemInit;
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
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("ALL")
public class GuideBookItem extends Item implements IAnimatable, ISyncable {
    public final AnimationFactory factory = new AnimationFactory(this);
    public boolean holdsItem = false;

    public static boolean isOpen() {
        return Registry.ITEM.getKey(LycanthropyItemInit.GUIDE_BOOK).equals(PatchouliAPI.get().getOpenBookGui());
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity && !world.isClient ){
            this.holdsItem = selected;
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public GuideBookItem(Settings settings) {
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
        animationData.addAnimationController(new AnimationController<>(this, "controllerName", 0, this::predicate));
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
    public void onAnimationSync(int id, int state) {}

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

}
