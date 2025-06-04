package it.eneaminelli.shopmanagement.inventory;

import it.eneaminelli.shopmanagement.item.Item;
import it.eneaminelli.shopmanagement.item.ItemFactory;
import it.eneaminelli.shopmanagement.item.ItemType;

public class InventoryManager {
    private ItemFactory itemFactory = new ItemFactory();
    private Inventory inventory = new Inventory();

    public Inventory getInventory() {
        return inventory;
    }

    public void createItem(){
        Item test = itemFactory.createItem(ItemType.TEST_ITEM_1, "Test 1", 99.99, "ABX", 9);
        System.out.println(test.toString());
        inventory.addItem(test);
    }
}
