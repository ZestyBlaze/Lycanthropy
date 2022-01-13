package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.entity.HunterEntity;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HunterEntityModel extends AnimatedGeoModel<HunterEntity> {
    private Identifier currentTexture = new Identifier(Lycanthropy.MODID, "textures/entity/villager/hunter.png");

    public void setCurrentTexture(Identifier currentTexture) {
        this.currentTexture = currentTexture;
    }

    @Override
    public Identifier getModelLocation(HunterEntity object) {
        return new Identifier(Lycanthropy.MODID, "geo/hunter.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HunterEntity object) {
        return currentTexture;
    }

    @Override
    public Identifier getAnimationFileLocation(HunterEntity animatable) {
        return new Identifier(Lycanthropy.MODID, "animations/hunter.animation.json");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(HunterEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
