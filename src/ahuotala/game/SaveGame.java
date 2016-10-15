package ahuotala.game;

import ahuotala.entities.Direction;
import ahuotala.entities.Player;

/**
 * Game save class used to load&save the game state
 * @author Aleksi Huotala
 */
public class SaveGame implements java.io.Serializable {

    /**
     * Default variables
     */
    //X
    private int x = 580;
    //Y
    private int y = 100;
    //Max health for the player
    private int health = Player.maxHealth;
    //0xp
    private double xp = 0.0;
    //Down -direction
    private Direction direction = Direction.DOWN;
    //Empty inventory
    private ItemStack[] inventory = new ItemStack[Inventory.cols * Inventory.rows];

    /**
     * Get player X
     * @return 
     */
    public int getX() {
        return x;
    }

    /**
     * Get player Y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Get player health
     * @return int
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get player XP
     * @return double
     */
    public double getXp() {
        return xp;
    }

    /**
     * Get player inventory
     * @return ItemStack[]
     */
    public ItemStack[] getInventory() {
        return inventory;
    }

    /**
     * Get player direction
     * @return Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Save game state
     * @param x
     * @param y
     * @param health
     * @param xp
     * @param direction
     * @param inventory 
     */
    public void saveState(int x, int y, int health, double xp, Direction direction, ItemStack[] inventory) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.xp = xp;
        this.direction = direction;
        this.inventory = inventory;
    }
}
