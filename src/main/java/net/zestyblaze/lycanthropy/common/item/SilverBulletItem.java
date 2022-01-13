package net.zestyblaze.lycanthropy.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.entity.SilverBulletEntity;

public class SilverBulletItem extends ArrowItem {
    public final float damage;

    public SilverBulletItem(float damageIn) {
        super(new Item.Settings().group(Lycanthropy.LYCANTHROPY_GROUP));
        this.damage = damageIn;
    }

    @Override
    public SilverBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        SilverBulletEntity silverBulletEntity = new SilverBulletEntity(worldIn, shooter, damage);
        silverBulletEntity.setDamage(this.damage);
        return silverBulletEntity;
    }

}