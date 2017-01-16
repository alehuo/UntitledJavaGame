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
package com.alehuo.untitledjavagame.singleplayer;

import com.alehuo.untitledjavagame.entities.Direction;
import com.alehuo.untitledjavagame.entities.Player;
import com.alehuo.untitledjavagame.item.Inventory;
import com.alehuo.untitledjavagame.item.ItemStack;

/**
 * Game save class used to load&save the game state
 *
 * @author alehuo
 */
public class SaveGame implements java.io.Serializable {

    /**
     * X -coordinate
     */
    private int x = 580;
    /**
     * Y -coordinate
     */
    private int y = 100;
    /**
     * Max health for the player
     */
    private int health = Player.maxHealth;
    /**
     * Starting xp
     */
    private double xp = 0.0;
    /**
     * Default direction
     */
    private Direction direction = Direction.DOWN;
    /**
     * Empty inventory
     */
    private ItemStack[] inventory = new ItemStack[Inventory.COLS * Inventory.ROWS];

    /**
     * Current game time
     */
    private int currentGameTime = 900;

    /**
     * Get player X
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Get player Y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Get player health
     *
     * @return int
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get player XP
     *
     * @return double
     */
    public double getXp() {
        return xp;
    }

    /**
     * Get player inventory
     *
     * @return ItemStack[]
     */
    public ItemStack[] getInventory() {
        return inventory;
    }

    /**
     * Get player direction
     *
     * @return Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     *
     * @return
     */
    public int getCurrentGameTime() {
        return currentGameTime;
    }

    /**
     * Save game state
     *
     * @param x
     * @param y
     * @param health
     * @param xp
     * @param direction
     * @param currentGameTime
     * @param inventory
     */
    public void saveState(int x, int y, int health, double xp, Direction direction, int currentGameTime, ItemStack[] inventory) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.xp = xp;
        this.direction = direction;
        this.currentGameTime = currentGameTime;
        this.inventory = inventory;
    }
}
