package ahuotala.game;

/**
 *
 * @author Aleksi Huotala
 */
public class ItemStack implements java.io.Serializable {

    private final int maxSize = 16;
    private int amount;
    private final Item item;
    private final String effect = "";
    private String label = "";

    public ItemStack(Item item, int amount) {
        this.item = item;
        if (this.amount > maxSize) {
            System.out.println("ItemStack too large; Defaulting to max stack size");
            this.amount = maxSize;
        } else {
            this.amount = amount;
        }
    }

    public ItemStack(Item item) {
        this.item = item;
        this.amount = 1;
    }

    public void setEffect(Effect effect, String desc) {
        item.setEffect(effect, desc);
    }

    public String getEffectDesc() {
        return item.getEffectDescription();
    }

    public Effect getEffect() {
        return item.getEffect();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return item.getName();
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
        return item.getItemId();
    }

    public boolean isInteractable() {
        return item.isInteractable();
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
        return (item.getItemId() + "").hashCode();
    }

    @Override
    public String toString() {
        return "itemId: " + item.getItemId() + ", amount: " + amount + "/" + maxSize; //To change body of generated methods, choose Tools | Templates.
    }

}
