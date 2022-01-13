package net.zestyblaze.lycanthropy.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.zestyblaze.lycanthropy.common.entity.WerewolfEntity;

public interface ILycanthropy extends ComponentV3, ServerTickingComponent {
    /**
     * Gives/Takes the ability to become a werewolf to a player
     * @param isWerewolf true to give a player the ability to become a werewolf
     */
    void setCanBecomeWerewolf(boolean isWerewolf);

    /**
     * Checks if a player has the ability to become a werewolf
     * @return true if player can become a werewolf, else false
     */
    boolean getCanBecomeWerewolf();

    /**
     * Checks if the player is currently in ist werewolf form
     * @return true of the player currently is in werewolf form.
     */
    boolean getIsWerewolf();

    void setIsWerewolf(boolean value);

    /**
     *
     * @param active if true will turn a player to an active werewolf if they can become a werewolf
     */
    void tryActivateWerewolfForm(boolean active);


    /**
     * increases the level of the Werewolf as long as its below max level
     */
    void increaseWerewolfLevel();

    /**
     * decreases the level of the Werewolf as long as its level is above zero;
     */
    void decreaseWerewolfLevel();


    /**
     * get the level of the werewolf
     * @return level
     */
    int getWerewolfLevel();

}
