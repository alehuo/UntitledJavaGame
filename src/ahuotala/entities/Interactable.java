package ahuotala.entities;

/**
 *
 * @author Aleksi Huotala
 */
interface Interactable {

    /**
     * Returns the x-radius
     *
     * @param int X -radius
     */
    void setInteractionRadiusX(int rX);

    /**
     * Returns the y-radius
     *
     * @param int Y -radius
     */
    void setInteractionRadiusY(int rY);

    /**
     * Returns the X-radius
     *
     * @return int X -radius
     */
    int getInteractionRadiusX();

    /**
     * Returns the y-radius
     *
     * @return int Y -radius
     */
    int getInteractionRadiusY();

    /**
     * Returns the NPC id
     *
     * @return int NPC id
     */
    int getNpcId();

    /**
     * Sets the NPC id
     *
     * @param int NPC id
     */
    void setNpcId(int id);

    /**
     * Returns if the player is within interaction distance of the NPC
     *
     * @param player
     * @return boolean
     */
    boolean isWithinInteractionDistance(Player player);
}
