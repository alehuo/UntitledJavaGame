package ahuotala.game;

import java.util.HashMap;

/**
 *
 * @author Aleksi Huotala
 */
public class ItemRegistry {

    private HashMap<ItemId, Item> items;

    public ItemRegistry() {
        items = new HashMap<>();
    }

    public Item registerItem(ItemId itemId) {
        if (!items.containsKey(itemId)) {
            items.put(itemId, new Item(itemId));
            return items.get(itemId);
        }
        return new Item(ItemId.NONE);
    }

    public Item getItem(ItemId itemId) {
        if (items.containsKey(itemId)) {
            return items.get(itemId);
        }
        return new Item(ItemId.NONE);
    }

    public HashMap<ItemId, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<ItemId, Item> items) {
        this.items = items;
    }

}
