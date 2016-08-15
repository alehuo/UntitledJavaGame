package ahuotala.game;

import ahuotala.entities.Direction;

/**
 *
 * @author Aleksi Huotala
 */
public class SaveGame implements java.io.Serializable {

    private int x;
    private int y;
    private int health;
    private double xp;
    private Direction direction;
    private ItemStack[] inventory;

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

    public ItemStack[] getInventory() {
        return inventory;
    }

    public Direction getDirection() {
        return direction;
    }

    public void saveState(int x, int y, int health, double xp, Direction direction, ItemStack[] inventory) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.xp = xp;
        this.direction = direction;
        this.inventory = inventory;
    }
}
