/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame.entities;

/**
 *
 * @author alehuo
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
