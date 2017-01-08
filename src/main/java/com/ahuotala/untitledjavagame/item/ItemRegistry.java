package com.ahuotala.untitledjavagame.item;

import com.ahuotala.untitledjavagame.item.ItemId;
import java.util.HashMap;

/**
 *
 * @author Aleksi Huotala
 */
public class ItemRegistry {

    /**
     * List of items
     */
    private HashMap<ItemId, Item> items;

    /**
     * ItemRegistry
     */
    public ItemRegistry() {
        items = new HashMap<>();
    }

    /**
     * Registers a new item
     *
     * @param itemId
     * @return Item that was created, or item that already exists from there
     */
    public Item registerItem(ItemId itemId) {
        if (!items.containsKey(itemId)) {
            items.put(itemId, new Item(itemId));
        }
        return items.get(itemId);
    }

    /**
     * Gets an item from ItemRegistry
     *
     * @param itemId ItemID
     * @return Item, or null if the item is not found
     */
    public Item getItem(ItemId itemId) {
        if (items.containsKey(itemId)) {
            return items.get(itemId);
        }
        return null;
    }

    /**
     * Returns all the items currently registered in ItemRegistry
     *
     * @return HashMap of items
     */
    public HashMap<ItemId, Item> getItems() {
        return items;
    }

    /**
     * Sets the items to ItemRegistry
     *
     * @param items HashMaps of items
     */
    public void setItems(HashMap<ItemId, Item> items) {
        this.items = items;
    }

}
