package net.zestyblaze.lycanthropy.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyComponentInit;
import net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Unique
    private static final Identifier LYCANTHROPY_GUI_ICONS_TEXTURE = new Identifier(Lycanthropy.MODID, "textures/gui/icons.png");
    @Unique
    private static final Identifier EMPTY_GUI_ICONS_TEXTURE = new Identifier(Lycanthropy.MODID, "textures/gui/icons_empty.png");

    @Shadow
    private int scaledHeight;
    @Shadow
    private int scaledWidth;
    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 0, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void renderRage(MatrixStack matrices, CallbackInfo callbackInfo) {
        PlayerEntity player = getCameraPlayer();
        if (LycanthropyUtils.isWerewolf(player)) {//TODO add rage level requirement
            RenderSystem.setShaderTexture(0, LYCANTHROPY_GUI_ICONS_TEXTURE);
            drawRage(matrices, player, scaledWidth / 2 - 91, scaledHeight - 39);
            RenderSystem.setShaderTexture(0, EMPTY_GUI_ICONS_TEXTURE);
        }
    }


    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 2, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void renderHunger(MatrixStack matrices, CallbackInfo callbackInfo) {
        PlayerEntity player = getCameraPlayer();
        if(LycanthropyUtils.isWerewolf(player)) {
            RenderSystem.setShaderTexture(0, LYCANTHROPY_GUI_ICONS_TEXTURE);
            drawHunger(matrices, player, scaledWidth / 2 + 82, scaledHeight - 39);
            RenderSystem.setShaderTexture(0, EMPTY_GUI_ICONS_TEXTURE);
        }
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 3, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void renderPostRage(MatrixStack matrices, CallbackInfo callbackInfo) {
        PlayerEntity player = getCameraPlayer();
        if (LycanthropyUtils.isWerewolf(player)) {
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
        }
    }
    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 1, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void renderPostHunger(MatrixStack matrices, CallbackInfo callbackInfo) {
        PlayerEntity player = getCameraPlayer();
        if (LycanthropyUtils.isWerewolf(player)) {
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
        }
    }

    /**
     *  @param matrices same stack as the rest of the HUD
     * @param entity the entity in
     * @param x screenspace x coord, increases in right direction
     * @param y screenspace y coord, increases in down direction
     */
    private void drawHunger(MatrixStack matrices, LivingEntity entity, int x, int y) {
        LycanthropyComponentInit.WEREWOLF_HUNGER.maybeGet(entity).ifPresent(werewolfHunger -> {
            int hunger_u = entity.hasStatusEffect(StatusEffects.HUNGER) ? 9*4 : 0;
            float hunger = (float)werewolfHunger.getHunger()/ werewolfHunger.getMaxHunger()* 20;
            int full = (int) hunger;
            //Draw empty steak icons
            for(int i = 0; i < 20; i++) {
                drawTexture(matrices, i >= 10 ? (x - i * 8) + 80 : (x - i * 8), y, 0, 0, 9, 9);
            }

            //Draw full steak icons
            for(int i = 0; i < full; i++) {
                drawTexture(matrices, i >= 10 ? (x - i * 8) + 80 : (x - i * 8), y, 9 + hunger_u, i >= 10 ? 9 : 0, 9, 9);
            }

            //draw half a steak icon
            if(full < 20) {
                float remaining = hunger - full;
                drawTexture(matrices, full >= 10 ? (x - full * 8) + 80 : (x - full * 8), y, remaining > 3 / 4f ? 9*2+hunger_u : remaining > 2 / 4f ? 9*3+hunger_u : remaining > 1 / 4f ? 9*4+hunger_u : 0, full >= 10 ? 9 : 0, 9, 9);
            }
        });
    }
    private void drawRage(MatrixStack matrices, LivingEntity entity, int x, int y) {
        LycanthropyComponentInit.WEREWOLF_RAGE.maybeGet(entity).ifPresent(werewolf -> {
            int rage = werewolf.getRage();
            //Draw empty steak icons
            for (int i = 0; i < 10; i++) {
                drawTexture(matrices, (x - i * 8) + 71, y-10, 0, 18, 9, 9);
            }

            //Draw full steak icons
            for (int i = 0; i < rage; i++) {
                drawTexture(matrices,  (x - i * 8) + 71, y-10, 9, 18, 9, 9);
            }
        });
    }
}
