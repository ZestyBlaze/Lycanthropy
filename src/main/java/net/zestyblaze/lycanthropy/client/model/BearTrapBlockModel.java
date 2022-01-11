package net.zestyblaze.lycanthropy.client.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.block.BearTrapBlock;
import net.zestyblaze.lycanthropy.common.block.blockentity.BearTrapBlockEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.molang.MolangParser;

public class BearTrapBlockModel extends AnimatedGeoModel<BearTrapBlockEntity> {
    public String getEntity(BearTrapBlock bearTrapBlockEntity){
        return Registry.BLOCK.getKey(bearTrapBlockEntity).get().getValue().getPath();
    }

    @Override
    public Identifier getModelLocation(BearTrapBlockEntity object) {
        return new Identifier(Lycanthropy.MODID, "geo/bear_trap.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BearTrapBlockEntity object) {
        return new Identifier(Lycanthropy.MODID, "textures/block/"+getEntity((BearTrapBlock) object.getCachedState().getBlock())+".png");
    }

    @Override
    public Identifier getAnimationFileLocation(BearTrapBlockEntity animatable) {
        return new Identifier(Lycanthropy.MODID, "animations/bear_trap.animation.json");
    }

    @Override
    public void setMolangQueries(IAnimatable animatable, double currentTick) {
        super.setMolangQueries(animatable, currentTick);
        MolangParser parser = GeckoLibCache.getInstance().parser;
        parser.setValue("query.winder", ((BearTrapBlockEntity) animatable).getWinder());
    }
}
