package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.item.WolfpeltArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WolfpeltArmorModel extends AnimatedGeoModel<WolfpeltArmorItem> {
    @Override
    public Identifier getModelLocation(WolfpeltArmorItem object) {
        return new Identifier(Lycanthropy.MODID, "geo/wolfpelt_armor.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WolfpeltArmorItem object) {
        return new Identifier(Lycanthropy.MODID, "textures/item/wolfpelt_armor.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WolfpeltArmorItem animatable) {
        return new Identifier(Lycanthropy.MODID, "animations/wolfpelt_armor.animation.json");
    }
}
