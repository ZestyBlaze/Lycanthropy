package net.zestyblaze.lycanthropy.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;

import java.util.Map;

public interface ILycantropy extends ComponentV3, ServerTickingComponent {
    void setWerewolf(boolean isWerewolf);
    boolean hasWerewolfForm();
    boolean isWerewolf();
    void increaseWerewolfLevel();
    void activateWerewolfForm(boolean active);
}
