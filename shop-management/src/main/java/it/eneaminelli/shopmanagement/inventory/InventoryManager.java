package it.eneaminelli.shopmanagement.inventory;

import java.util.ArrayList;
import java.util.List;

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

    public Item getFirstItem(){
        List<Item> itemList = new ArrayList<>();

        itemList = inventory.getItemList();
        
        try {
            return itemList.get(0);
        } catch (Exception e) {
            System.out.println("Error in retrieving item list: " + e);
        }

        return null;

    }
}
