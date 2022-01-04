package net.zestyblaze.lycanthropy.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.registry.LycanthropyComponentInit;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebugItem extends Item {
    public DebugItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(new TranslatableText("text.lycanthropy.hold_shift").formatted(Formatting.GRAY, Formatting.ITALIC));
        } else {
            tooltip.add(new TranslatableText("text.lycanthropy.debug_item.tooltip").formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        LycanthropyComponentInit.WEREWOLF.maybeGet(user).ifPresent(werewolfComponent -> {
            if(!werewolfComponent.isWerewolf) {
                werewolfComponent.setWerewolf(true);
                werewolfComponent.activateWerewolfForm(true);
                user.sendMessage(new TranslatableText("text.lycanthropy.debug_item.success_add"), true);
            } else if(user.isSneaking() && werewolfComponent.isWerewolf) {
                werewolfComponent.setWerewolf(false);
                werewolfComponent.activateWerewolfForm(false);
                user.sendMessage(new TranslatableText("text.lycanthropy.debug_item.success_remove"), true);
            } else {
                user.sendMessage(new TranslatableText("text.lycanthropy.debug_item.fail"), true);
            }
        });
        return TypedActionResult.success(stack);
    }
}
