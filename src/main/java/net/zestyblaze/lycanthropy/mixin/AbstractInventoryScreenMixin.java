package net.zestyblaze.lycanthropy.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.statuseffect.LycanthropyStatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
    @Unique
    private boolean isBackgroundSwitched;
    @Unique
    private static final Identifier LYCANTHROPY_BACKGROUND_TEXTURE = new Identifier(Lycanthropy.MODID, "textures/gui/inventory.png");
    public AbstractInventoryScreenMixin(T container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
    }

    @ModifyVariable(method = "drawStatusEffectBackgrounds", at = @At(value = "STORE"))
    private StatusEffectInstance customizeDrawnBackground(StatusEffectInstance effect) {
        if (effect.getEffectType() instanceof LycanthropyStatusEffect) {
            assert client != null;
            RenderSystem.setShaderTexture(0, LYCANTHROPY_BACKGROUND_TEXTURE);
            isBackgroundSwitched = true;
        }
        return effect;
    }

    @Inject(method = "drawStatusEffectBackgrounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", shift = At.Shift.AFTER))
    private void restoreDrawnBackground(MatrixStack matrices, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean bl, CallbackInfo ci) {
        if (isBackgroundSwitched) {
            assert client != null;
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            isBackgroundSwitched = false;
        }
    }
}