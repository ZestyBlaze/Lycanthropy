package net.zestyblaze.lycanthropy.common.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.utils.LycanthropyDamageSources;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class WerewolfToothItem extends Item {
    public WerewolfToothItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(new TranslatableText("text.lycanthropy.hold_shift").formatted(Formatting.GRAY, Formatting.ITALIC));
        } else {
            tooltip.add(new TranslatableText("text.lycanthropy.werewolf_tooth.tooltip").formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(!world.isClient()) {
            if(world.isNight()) {
                if (world.getDimension().getMoonPhase(world.getLunarTime()) == 0) {
                    ///TODO: Custom Potion Effect?
                    if(!user.hasStatusEffect(StatusEffects.STRENGTH) || Objects.requireNonNull(user.getStatusEffect(StatusEffects.STRENGTH)).getAmplifier() <= 0) {
                        if(!user.isCreative()) {
                            stack.decrement(1);
                            user.damage(LycanthropyDamageSources.FAILED_RITUAL, user.getHealth());
                        }
                    } else {
                        ///TODO: Add a Werewolf Sequence here
                        if(!user.isCreative()) {
                            stack.decrement(1);
                        }
                        user.sendMessage(new LiteralText("Werewolf Time"), false);
                    }
                } else {
                    user.sendMessage(new TranslatableText("text.lycanthropy.werewolf_tooth.fail1").formatted(Formatting.RED), true);
                }
            } else {
                user.sendMessage(new TranslatableText("text.lycanthropy.werewolf_tooth.fail2").formatted(Formatting.RED), true);
            }
        }
        return TypedActionResult.fail(stack);
    }
}
