package net.zestyblaze.lycanthropy.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.registry.LycanthropySoundEvents;

public class WerewolfToothItem extends Item {
    private static final int MAX_USE_TIME = 60;
    public WerewolfToothItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient){
            if(world.isNight()){
                if(world.getDimension().getMoonPhase(world.getLunarTime()) == 0){
                    if(user instanceof PlayerEntity player && !player.isCreative()){
                        LycanthropyComponentInit.WEREWOLF.get(player).setCanBecomeWerewolf(true);
                        world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS,1,1);
                        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.PLAYERS,0.5F,1.5F);
                        world.playSound(null, player.getBlockPos(), LycanthropySoundEvents.ENTITY_WEREWOLF_HOWL, SoundCategory.PLAYERS,0.5F,1);
                        player.sendMessage(new TranslatableText("text.lycanthropy.ritual.success").formatted(Formatting.GRAY, Formatting.ITALIC), false);
                        stack.damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                        return stack;
                    }
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return MAX_USE_TIME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
