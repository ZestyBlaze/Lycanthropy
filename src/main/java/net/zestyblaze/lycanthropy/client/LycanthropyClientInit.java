package net.zestyblaze.lycanthropy.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.api.event.RenderEvents;
import net.zestyblaze.lycanthropy.client.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.client.renderer.*;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.common.registry.*;
import net.zestyblaze.lycanthropy.common.utils.LycanthropyUtils;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

@SuppressWarnings("unchecked")
public class LycanthropyClientInit {
    public static KeyBinding screenKey;
    private static boolean keyBoolean;

    public static void registerRenderer() {
        EntityRendererRegistry.register(LycanthropyEntityTypeInit.WEREWOLF, WerewolfEntityRenderer::new);
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



        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Client Registered");
        }
    }

    public static void registerClientEvents() {
        screenKey = new KeyBinding("key.lycanthropy.openSkillTree", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.lycanthropy.keybind");
        KeyBindingHelper.registerKeyBinding(screenKey);

        /*
         * Using the RenderEvents to render the Werewolf model, a new one only if the player doesn't already have one.
         * Copy all player properties to the entity of the werewolf.
         */
        RenderEvents.PLAYER.register((player, tickDelta, matrixStack, vertexConsumerProvider, light) -> {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if(!LycanthropyUtils.isWerewolf(player))return;
            if (LycanthropyComponentInit.WEREWOLF.get(player).getRenderedWerewolfEntity() == null){
                WerewolfEntity werewolfEntity = LycanthropyEntityTypeInit.WEREWOLF.create(player.world);
                LycanthropyComponentInit.WEREWOLF.get(player).setRenderedWerewolfEntity(werewolfEntity);
            }
            try {
                matrixStack.push();
                WerewolfEntity werewolfEntity = LycanthropyComponentInit.WEREWOLF.get(player).getRenderedWerewolfEntity();
                EntityRenderer<? super WerewolfEntity> werewolfRenderer = minecraftClient.getEntityRenderDispatcher().getRenderer(werewolfEntity);
                if(player != minecraftClient.player || !MinecraftClient.getInstance().options.getPerspective().isFirstPerson()){
                    werewolfEntity.age = player.age;
                    werewolfEntity.hurtTime = player.hurtTime;
                    werewolfEntity.maxHurtTime = Integer.MAX_VALUE;
                    werewolfEntity.limbDistance = player.limbDistance;
                    werewolfEntity.lastLimbDistance = player.lastLimbDistance;
                    werewolfEntity.limbAngle = player.limbAngle;
                    werewolfEntity.headYaw = player.headYaw;
                    werewolfEntity.prevHeadYaw = player.prevHeadYaw;
                    werewolfEntity.bodyYaw = player.bodyYaw;
                    werewolfEntity.prevBodyYaw = player.prevBodyYaw;
                    werewolfEntity.handSwinging = player.handSwinging;
                    werewolfEntity.handSwingTicks = player.handSwingTicks;
                    werewolfEntity.handSwingProgress = player.handSwingProgress;
                    werewolfEntity.lastHandSwingProgress = player.lastHandSwingProgress;
                    werewolfEntity.setPitch(player.getPitch());
                    werewolfEntity.prevPitch = player.prevPitch;
                    werewolfEntity.preferredHand = player.preferredHand;
                    werewolfEntity.setStackInHand(Hand.MAIN_HAND, player.getMainHandStack());
                    werewolfEntity.setStackInHand(Hand.OFF_HAND, player.getOffHandStack());
                    werewolfEntity.setCurrentHand(player.getActiveHand() == null ? Hand.MAIN_HAND : player.getActiveHand());
                    werewolfEntity.setSneaking(player.isSneaking());
                    werewolfEntity.motionCalc = new Vec3d(player.getX()-player.prevX, player.getY()-player.prevY,player.getZ()-player.prevZ);
                    werewolfEntity.isSneaking();
                    werewolfEntity.forwardSpeed=player.forwardSpeed;
                    werewolfEntity.setPose(player.getPose());
                    werewolfEntity.setSprinting(player.isSprinting());
                    werewolfRenderer.render(werewolfEntity, player.bodyYaw, tickDelta, matrixStack, vertexConsumerProvider, light);
                }
            }catch (Throwable throwable){
                if (!(throwable instanceof NullPointerException))
                    throwable.printStackTrace();
                matrixStack.pop();
            }finally {
                matrixStack.pop();
            }
        });


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
