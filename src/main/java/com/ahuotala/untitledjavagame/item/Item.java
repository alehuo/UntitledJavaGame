package com.ahuotala.untitledjavagame.item;

import com.ahuotala.untitledjavagame.item.ItemId;

/**
 *
 * @author Aleksi Huotala
 */
public class Item implements java.io.Serializable {

    /**
     * Item name
     */
    private String name;
    
    /**
     * Item effect
     */
    private Effect effect;
    
    /**
     * Effect description
     */
    private String effectDesc;
    
    /**
     * Effect value
     */
    private int effectValue;
    
    /**
     * Item ID
     */
    private final ItemId itemId;
    
    /**
     * Is the item interactable or not
     */
    private boolean isInteractable = false;

    /**
     * Item
     * @param itemId 
     */
    public Item(ItemId itemId) {
        this.itemId = itemId;
        this.name = itemId + "";
        effect = Effect.NONE;
        effectValue = 0;
    }

    /**
     * Sets the item name
     * @param name Item name
     * @return Item
     */
    public Item setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the item to be interactable or not
     * @param value Interaction status
     * @return Item
     */
    public Item setInteractable(boolean value) {
        isInteractable = value;
        return this;
    }

    /**
     * Sets the item effect
     * @param effect Effect
     * @param desc Description
     * @return Item
     */
    public Item setEffect(Effect effect, String desc) {
        this.effect = effect;
        this.effectDesc = desc;
        return this;
    }

    /**
     * Sets the item effect value
     * @param effectValue Effect value
     * @return Item
     */
    public Item setEffectValue(int effectValue) {
        this.effectValue = effectValue;
        return this;
    }

    /**
     * Returns the effect value
     * @return Effect value
     */
    public int getEffectValue() {
        return effectValue;
    }

    /**
     * Returns the Effect
     * @return Effect
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Returns the item's ID
     * @return ItemID
     */
    public ItemId getItemId() {
        return itemId;
    }

    /**
     * Returns the effect description
     * @return Effect description
     */
    public String getEffectDescription() {
        return effectDesc;
    }

    /**
     * Returns the item's name
     * @return Item name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns if the item is interactable or not
     * @return true or false
     */
    public boolean isInteractable() {
        return isInteractable;
    }

    /**
     * Interact
     */
    public void interact() {

    }
}
