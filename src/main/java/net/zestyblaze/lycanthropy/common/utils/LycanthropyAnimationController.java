package net.zestyblaze.lycanthropy.common.utils;

import com.eliotlash.molang.MolangParser;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.HashMap;
import java.util.List;

public class LycanthropyAnimationController extends AnimationController{
    public LycanthropyAnimationController(IAnimatable animatable, String name, int transitionLength, AnimationController.IAnimationPredicate<WerewolfEntity> predicate) {
        super(animatable, name, transitionLength, predicate);
    }

    public double speed = 1;
    public double lastTick = 0;

    @Override
    public void process(double tick, AnimationEvent event, List modelRendererList, HashMap boneSnapshotCollection, MolangParser parser, boolean crashWhenCantFindBone) {
        double tickDif = tick - lastTick;
        lastTick = tick;
        super.process(tick + (tickDif * (speed - 1.0)), event, modelRendererList, boneSnapshotCollection, parser, crashWhenCantFindBone);
    }
}
