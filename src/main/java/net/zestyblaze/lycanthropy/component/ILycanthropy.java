package net.zestyblaze.lycanthropy.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.zestyblaze.lycanthropy.entity.WerewolfEntity;

public interface ILycanthropy extends ComponentV3, ServerTickingComponent {
    /**
     * Gives/Takes the ability to become a werewolf to a player
     * @param isWerewolf true to give a player the ability to become a werewolf
     */
    void setWerewolf(boolean isWerewolf);

    /**
     * Checks if a player has the ability to become a werewolf
     * @return true if player can become a werewolf, else false
     */
    boolean hasWerewolfForm();

    /**
     * Checks if the player is currently in ist werewolf form
     * @return true of the player currently is in werewolf form.
     */
    boolean isWerewolf();

    /**
     * Activates a players werewolf form
     * @param active, true to activate, false to deactivate
     */
    void activateWerewolfForm(boolean active);

    /**
     * Checks if the player has a rendered werewolf entity
     * @return the werewolf entity
     */
    WerewolfEntity hasWerewolfEntity();

    /**
     * Assigns a werewolfEntity to a player
     * @param werewolf the created entity
     */
    void setWerewolfEntity(WerewolfEntity werewolf);

    /**
     * increases the level of the Werewolf as long as its below max level
     */
    void increaseWerewolfLevel();

    /**
     * decreases the level of the Werewolf as long as its level is above zero;
     */
    void decreaseWerewolfLevel();

    /**
     * set the level of the werewolf
     * @param level level
     */
    void setWerewolfLevel(int level);

    /**
     * get the level of teh werewolf
     * @return level
     */
    int getWerewolfLevel();
}
