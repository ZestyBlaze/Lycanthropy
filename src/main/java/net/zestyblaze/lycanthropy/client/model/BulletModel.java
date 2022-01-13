package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.entity.SilverBulletEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BulletModel extends AnimatedGeoModel<SilverBulletEntity> {
    @Override
    public Identifier getModelLocation(SilverBulletEntity object) {
        return new Identifier(Lycanthropy.MODID, "geo/bullet.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SilverBulletEntity object) {
        return new Identifier(Lycanthropy.MODID, "textures/entity/bullet.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SilverBulletEntity animatable) {
        return new Identifier(Lycanthropy.MODID, "animations/bullet.animation.json");
    }
}