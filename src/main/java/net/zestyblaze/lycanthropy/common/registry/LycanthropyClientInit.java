package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Hand;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.client.renderer.GuideBookItemRenderer;
import net.zestyblaze.lycanthropy.client.renderer.WerewolfEntityRenderer;
import net.zestyblaze.lycanthropy.common.config.LycanthropyModConfig;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import net.zestyblaze.lycanthropy.api.event.RenderEvents;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class LycanthropyClientInit {
    public static void registerRenderer() {
        EntityRendererRegistry.register(LycanthropyEntityTypeInit.WEREWOLF, WerewolfEntityRenderer::new);
        GeoItemRenderer.registerItemRenderer(LycanthropyItemInit.GUIDE_BOOK_DEV, new GuideBookItemRenderer());
        BlockRenderLayerMap.INSTANCE.putBlock(LycanthropyBlockInit.BONE_PILE, RenderLayer.getCutout());

        if(LycanthropyModConfig.get().debugMode) {
            Lycanthropy.LOGGER.info("Lycanthropy: Registry - Client Registered");
        }
    }

    public static void registerClientEvents() {

        /*
         * Using the RenderEvents to render the Werewolf model, a new one only if the player doesn't already have one.
         * Copy all player properties to the entity of the werewolf.
         */
        RenderEvents.PLAYER.register((player, tickDelta, matrixStack, vertexConsumerProvider, light) -> {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if(!LycanthropyComponentInit.WEREWOLF.get(player).canBecomeWerewolf)return;
            if (LycanthropyComponentInit.WEREWOLF.get(player).hasWerewolfEntity() == null){
                WerewolfEntity werewolfEntity = LycanthropyEntityTypeInit.WEREWOLF.create(player.world);
                LycanthropyComponentInit.WEREWOLF.get(player).setWerewolfEntity(werewolfEntity);
            }
            try {
                matrixStack.push();
                WerewolfEntity werewolfEntity = LycanthropyComponentInit.WEREWOLF.get(player).hasWerewolfEntity();
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
                    werewolfEntity.isSneaking();
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

        ClientTickEvents.END_WORLD_TICK.register(world -> {
            //TODO add keybinds here
        });
    }
}
