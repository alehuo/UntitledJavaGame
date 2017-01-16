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
package com.alehuo.untitledjavagame.item;

/**
 *
 * @author alehuo
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
