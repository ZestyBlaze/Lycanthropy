package net.zestyblaze.lycanthropy.common.item.tool.material;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.zestyblaze.lycanthropy.common.registry.LycanthropyItemInit;

public class SilverToolMaterial implements ToolMaterial {

    public static final SilverToolMaterial INSTANCE = new SilverToolMaterial();

    @Override
    public int getDurability() {
        return 175;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 5.0f;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 7;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(LycanthropyItemInit.SILVER_INGOT);
    }
}
