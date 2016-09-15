/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

/**
 *
 * @author Aleksi Huotala
 */
interface Interactable {

    /**
     * Set x-radius
     *
     * @param int rX
     */
    void setInteractionRadiusX(int rX);

    /**
     * Set y-radius
     *
     * @param int rY
     */
    void setInteractionRadiusY(int rY);

    /**
     * Get the x-radius
     *
     * @return int
     */
    int getInteractionRadiusX();

    /**
     * Get the y-radius
     *
     * @return int
     */
    int getInteractionRadiusY();

    /**
     * Get the NPC id
     *
     * @return int
     */
    int getNpcId();

    /**
     * Set the NPC id
     *
     * @param int id
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
