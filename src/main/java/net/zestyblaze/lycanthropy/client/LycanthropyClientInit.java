package net.zestyblaze.lycanthropy.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.client.renderer.*;
import net.zestyblaze.lycanthropy.common.registry.*;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

@SuppressWarnings("unchecked")
public class LycanthropyClientInit {
    public static KeyBinding screenKey;
    private static boolean keyBoolean;

    public static void registerRenderer() {
        EntityRendererRegistry.register(LycanthropyEntityTypeInit.WEREWOLF, WerewolfEntityRenderer::new);
        EntityRendererRegistry.register(LycanthropyEntityTypeInit.HUNTER_VILLAGER, HunterEntityRenderer::new);
        if(LycanthropyModConfig.get().modelBook3D){
            GeoItemRenderer.registerItemRenderer(LycanthropyItemInit.GUIDE_BOOK, new GuideBookItemRenderer());
        }
        BlockRenderLayerMap.INSTANCE.putBlock(LycanthropyBlockInit.BONE_PILE, RenderLayer.getCutout());
        GeoArmorRenderer.registerArmorRenderer(new WolfpeltArmorRenderer(), LycanthropyItemInit.WOLF_PELT_HEAD,
        LycanthropyItemInit.WOLF_PELT_CHEST, LycanthropyItemInit.WOLF_PELT_LEGGINGS, LycanthropyItemInit.WOLF_PELT_BOOTS);
        GeoItemRenderer.registerItemRenderer(LycanthropyItemInit.FLINTLOCK_RIFLE, new FlintlockItemRenderer());
        BlockEntityRendererRegistry.register(LycanthropyBlockEntityInit.CAGE_BLOCK_ENTITY,
        (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new CageBlockRenderer());
        BlockRenderLayerMapImpl.INSTANCE.putBlock(LycanthropyBlockInit.CAGE_BLOCK, RenderLayer.getCutout());

        BlockEntityRendererRegistry.register(LycanthropyBlockEntityInit.BEAR_TRAP_BLOCK_ENTITY,
        (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new BearTrapBlockRenderer());
        BlockRenderLayerMapImpl.INSTANCE.putBlock(LycanthropyBlockInit.BEAR_TRAP_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMapImpl.INSTANCE.putBlock(LycanthropyBlockInit.SILVER_BEAR_TRAP_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), LycanthropyBlockInit.WOLFSBANE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), LycanthropyBlockInit.WOLFSBANE);

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Client Registered");
        }
    }

    public static void registerClientEvents() {
        screenKey = new KeyBinding("key.lycanthropy.openSkillTree", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.lycanthropy.keybind");
        KeyBindingHelper.registerKeyBinding(screenKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(screenKey.wasPressed()) {
                if(!keyBoolean) {
                    Lycanthropy.LOGGER.warn("Lycanthropy - Client: Key Pressed");
                    LycanthropyComponentInit.WEREWOLF.maybeGet(client.player).ifPresent(werewolf -> {
                        if(werewolf.getIsWerewolf()) {
                            ///TODO: Set Screen Here
                            Lycanthropy.LOGGER.warn("Lycanthropy - Client: Screen Triggered");
                        } else {
                            assert client.player != null;
                            client.player.sendMessage(new TranslatableText("key.lycanthropy.openSkillTree.fail").formatted(Formatting.RED, Formatting.ITALIC), true);
                        }
                    });
                }
                keyBoolean = true;
            } else if(keyBoolean) {
                keyBoolean = false;
            }
        });
    }
}
