package com.ahuotala.untitledjavagame.game;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksi Huotala
 */
public class ItemStack implements java.io.Serializable {

    /**
     * Stack max size
     */
    private final int maxSize = 16;

    /**
     * Amount of items in a stack
     */
    private int amount;

    /**
     * Item object
     */
    private final Item item;

    /**
     * Itemstack label
     */
    private String label;

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(ItemStack.class.getName());

    public ItemStack(Item item, int amount) {
        this.item = item;
        if (this.amount > maxSize) {
            LOG.log(Level.INFO, null, "ItemStack too large; Defaulting to max stack size");
            this.amount = maxSize;
        } else {
            this.amount = amount;
        }
    }

    /**
     * ItemStack
     *
     * @param item Item
     */
    public ItemStack(Item item) {
        this.item = item;
        this.amount = 1;
    }

    /**
     * Sets the item effect
     *
     * @param effect Item effect
     * @param desc Description
     */
    public void setEffect(Effect effect, String desc) {
        item.setEffect(effect, desc);
    }

    /**
     * Returns the effect description
     *
     * @return Effect description
     */
    public String getEffectDesc() {
        return item.getEffectDescription();
    }

    /**
     * Returns the effect
     *
     * @return Effect
     */
    public Effect getEffect() {
        return item.getEffect();
    }

    /**
     * Sets the item label
     *
     * @param label Item label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the item label
     *
     * @return Item label
     */
    public String getLabel() {
        return item.getName();
    }

    /**
     * Take item(s) from the stack
     *
     * @param amount Amount of items
     */
    public void takeFromStack(int amount) {
        if (this.amount - amount >= 0) {
            this.amount -= amount;
        }
    }

    /**
     * Add item(s) to the stac
     *
     * @param amount Amount of items
     */
    public void addToStack(int amount) {
        if (this.amount + amount <= maxSize) {
            this.amount += amount;
        }
    }

    /**
     * Returns the item id
     *
     * @return Item id
     */
    public ItemId getItemId() {
        return item.getItemId();
    }

    /**
     * Returns if the itemstack is interactable
     * @return true or false
     */
    public boolean isInteractable() {
        return item.isInteractable();
    }

    /**
     * Returns the amount if items in a stack
     * @return Amount of items in stack
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the maximum size of the itemstack
     * @return Maximum amount of items in an itemstack
     */
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
