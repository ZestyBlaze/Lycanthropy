package net.zestyblaze.lycanthropy.client.renderer;

import net.zestyblaze.lycanthropy.client.model.FlintlockItemModel;
import net.zestyblaze.lycanthropy.common.item.FlintlockItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class FlintlockItemRenderer extends GeoItemRenderer<FlintlockItem> {
    public FlintlockItemRenderer() {
        super(new FlintlockItemModel());
    }
}