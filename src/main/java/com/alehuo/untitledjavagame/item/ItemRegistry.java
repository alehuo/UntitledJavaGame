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

import java.util.HashMap;

/**
 *
 * @author alehuo
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
