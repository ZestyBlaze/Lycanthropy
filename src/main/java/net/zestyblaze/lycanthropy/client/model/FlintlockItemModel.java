package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.item.FlintlockItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlintlockItemModel extends AnimatedGeoModel<FlintlockItem> {
    @Override
    public Identifier getModelLocation(FlintlockItem object) {
        return new Identifier(Lycanthropy.MODID, "geo/flintlock.geo.json");
    }

    @Override
    public Identifier getTextureLocation(FlintlockItem object) {
        return new Identifier(Lycanthropy.MODID, "textures/item/flintlock.png");
    }

    @Override
    public Identifier getAnimationFileLocation(FlintlockItem animatable) {
        return new Identifier(Lycanthropy.MODID, "animations/flintlock.animation.json");
    }
}
