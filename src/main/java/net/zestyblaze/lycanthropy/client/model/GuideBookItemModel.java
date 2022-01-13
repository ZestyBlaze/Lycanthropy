package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.item.GuideBookItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GuideBookItemModel extends AnimatedGeoModel<GuideBookItem> {
    @Override
    public Identifier getAnimationFileLocation(GuideBookItem entity) {
        return new Identifier(Lycanthropy.MODID, "animations/guide_book.animation.json");
    }

    @Override
    public Identifier getModelLocation(GuideBookItem animatable) {
        return new Identifier(Lycanthropy.MODID, "geo/guide_book.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GuideBookItem entity) {
        return new Identifier(Lycanthropy.MODID, "textures/item/guide_book.png");
    }
}