package net.zestyblaze.lycanthropy.client.renderer;

import net.zestyblaze.lycanthropy.client.model.WolfpeltArmorModel;
import net.zestyblaze.lycanthropy.common.item.WolfpeltArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class WolfpeltArmorRenderer extends GeoArmorRenderer<WolfpeltArmorItem> {
    public WolfpeltArmorRenderer() {
        super(new WolfpeltArmorModel());

        // These values are what each bone name is in blockbench. So if your head bone
        // is named "bone545", make sure to do this.headBone = "bone545";
        // The default values are the ones that come with the default armor template in
        // the geckolib blockbench plugin.
        this.headBone = "helmet";
        this.bodyBone = "chestplate";
        this.rightArmBone = "rightArm";
        this.leftArmBone = "leftArm";
        this.rightLegBone = "rightLeg";
        this.leftLegBone = "leftLeg";
        this.rightBootBone = "rightBoot";
        this.leftBootBone = "leftBoot";
    }
}
