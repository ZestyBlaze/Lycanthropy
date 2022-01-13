package net.zestyblaze.lycanthropy.common.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyStatusEffectsInit;
import net.zestyblaze.lycanthropy.common.utils.LycanthropyDamageSources;

import java.util.Objects;

public class WerewolfToothItem extends Item {
    public WerewolfToothItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(!world.isClient()) {
            if(world.isNight()) {
                if (world.getDimension().getMoonPhase(world.getLunarTime()) == 0) {
                    if(!user.hasStatusEffect(LycanthropyStatusEffectsInit.BEAST_SENSE) || Objects.requireNonNull(user.getStatusEffect(LycanthropyStatusEffectsInit.BEAST_SENSE)).getAmplifier() <= 0) {
                        if(!user.isCreative()) {
                            stack.decrement(1);
                            user.damage(LycanthropyDamageSources.FAILED_RITUAL, user.getHealth() + user.getAbsorptionAmount());
                        }
                    } else {
                        if(!user.isCreative()) {
                            stack.decrement(1);
                        }
                        LycanthropyComponentInit.WEREWOLF.maybeGet(user).ifPresent(werewolfComponent -> werewolfComponent.setIsWerewolf(true));
                        user.setStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 9, false, false), null);
                        user.setStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160, 0, false, false), null);
                    }   user.sendMessage(new TranslatableText("text.lycanthropy.ritual.success").formatted(Formatting.GRAY, Formatting.ITALIC), false);
                } else {
                    return TypedActionResult.fail(stack);
                }
            } else {
                return TypedActionResult.fail(stack);
            }
        }
        return TypedActionResult.fail(stack);
    }
}
