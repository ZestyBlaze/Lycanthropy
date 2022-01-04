package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WerewolfEntityModel extends AnimatedGeoModel<WerewolfEntity> {
    @Override
    public Identifier getModelLocation(WerewolfEntity object) {
        return new Identifier(Lycanthropy.MODID, "geo/werewolf.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WerewolfEntity object) {
        return new Identifier(Lycanthropy.MODID, "textures/entity/werewolf/werewolf.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WerewolfEntity animatable) {
        return new Identifier(Lycanthropy.MODID, "animations/werewolf.animation.json");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(WerewolfEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("skull");
        IBone leftLeg = this.getAnimationProcessor().getBone("leftLeg");
        IBone rightLeg = this.getAnimationProcessor().getBone("rightLeg");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}