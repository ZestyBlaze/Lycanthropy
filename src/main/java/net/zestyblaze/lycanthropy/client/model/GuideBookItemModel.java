package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.item.GuideBookDevItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GuideBookItemModel extends AnimatedGeoModel<GuideBookDevItem> {
    @Override
    public Identifier getAnimationFileLocation(GuideBookDevItem entity) {
        return new Identifier(Lycanthropy.MODID, "animations/guide_book.animation.json");
    }

    @Override
    public Identifier getModelLocation(GuideBookDevItem animatable) {
        return new Identifier(Lycanthropy.MODID, "geo/guide_book.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GuideBookDevItem entity) {
        return new Identifier(Lycanthropy.MODID, "textures/item/guide_book_dev.png");
    }
}