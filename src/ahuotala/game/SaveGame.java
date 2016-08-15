/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import ahuotala.entities.Direction;
import ahuotala.entities.Player;

/**
 *
 * @author Aleksi
 */
public class SaveGame implements java.io.Serializable {

    private int x;
    private int y;
    private int health;
    private double xp;
    private Direction direction;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public double getXp() {
        return xp;
    }

    public Direction getDirection() {
        return direction;
    }

    public void saveState(int x, int y, int health, double xp, Direction direction) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.xp = xp;
        this.direction = direction;
    }
}
