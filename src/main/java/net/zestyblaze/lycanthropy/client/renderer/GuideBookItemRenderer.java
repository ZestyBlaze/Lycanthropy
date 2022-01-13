package net.zestyblaze.lycanthropy.client.renderer;

import net.zestyblaze.lycanthropy.client.model.GuideBookItemModel;
import net.zestyblaze.lycanthropy.common.item.GuideBookItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GuideBookItemRenderer extends GeoItemRenderer<GuideBookItem> {
    public GuideBookItemRenderer() {
        super(new GuideBookItemModel());
    }
}