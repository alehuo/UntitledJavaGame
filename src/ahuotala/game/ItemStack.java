package ahuotala.game;

/**
 *
 * @author Aleksi Huotala
 */
public class ItemStack {

    private final int maxSize = 16;
    private int amount;
    private final ItemId itemId;
    private String effect = "";

    public ItemStack(ItemId itemId, int amount) {
        this.itemId = itemId;
        if (this.amount > maxSize) {
            this.amount = maxSize;
        } else {
            this.amount = amount;
        }
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }

    public ItemStack(ItemId itemId) {
        this.itemId = itemId;
        this.amount = 1;
    }

    public void takeFromStack(int amount) {
        if (this.amount - amount >= 0) {
            this.amount -= amount;
        }
    }

    public void addToStack(int amount) {
        if (this.amount + amount <= maxSize) {
            this.amount += amount;
        }
    }

    public ItemId getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        ItemStack stack2 = (ItemStack) obj;
        return stack2.getItemId() == this.getItemId();
    }

    @Override
    public int hashCode() {
        return (itemId + "").hashCode();
    }

    @Override
    public String toString() {
        return "itemId: " + itemId + ", amount: " + amount + "/" + maxSize; //To change body of generated methods, choose Tools | Templates.
    }

}
