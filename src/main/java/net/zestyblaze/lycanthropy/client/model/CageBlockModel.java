package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.block.blockentity.CageBlockEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CageBlockModel extends AnimatedGeoModel<CageBlockEntity> {
    @Override
    public Identifier getModelLocation(CageBlockEntity object) {
        return new Identifier(Lycanthropy.MODID, "geo/cage.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CageBlockEntity object) {
        return new Identifier(Lycanthropy.MODID, "textures/block/cage.png");
    }

    @Override
    public Identifier getAnimationFileLocation(CageBlockEntity animatable) {
        return new Identifier(Lycanthropy.MODID, "animations/cage.animation.json");
    }
}
