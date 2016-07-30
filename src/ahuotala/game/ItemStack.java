package ahuotala.game;

/**
 *
 * @author Aleksi Huotala
 */
public class ItemStack {

    private final int maxSize = 16;
    private int amount;
    private final int itemId;

    public ItemStack(int itemId, int amount) {
        this.itemId = itemId;
        if (this.amount > maxSize) {
            this.amount = maxSize;
        } else {
            this.amount = amount;
        }

    }

    public ItemStack(int itemId) {
        this.itemId = itemId;
        this.amount = 1;
    }

    public void takeFromStack(int amount) {
        if (this.amount - amount >= 0) {
            this.amount -= amount;
        }
    }

    public int getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public int getMaxSize() {
        return maxSize;
    }

}
