package net.zestyblaze.lycanthropy.common.registry;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class LycanthropyTagsInit {
    public static final Tag<Item> MEAT = TagFactory.ITEM.create(new Identifier("c", "meat"));
}
