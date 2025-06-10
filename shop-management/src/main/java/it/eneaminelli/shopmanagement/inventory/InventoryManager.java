package it.eneaminelli.shopmanagement.inventory;

import java.util.ArrayList;
import java.util.List;

import it.eneaminelli.shopmanagement.item.Item;
import it.eneaminelli.shopmanagement.item.ItemFactory;
import it.eneaminelli.shopmanagement.strategies.ItemCreationStrategy;

public class InventoryManager {
    private ItemFactory itemFactory = new ItemFactory();
    private Inventory inventory = new Inventory();

    //Entry point for creating an item - new objects must implement strategy interface!
    public void createItem(ItemCreationStrategy strategy){
        if(strategy == null){
            System.err.println("Strategy cannot be null.");
            return;
        }

        //Delegating construction logic to strategies

        Item newItem = strategy.create();
        System.out.println("Item created: " + newItem.toString());
        inventory.addItem(newItem);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Item getFirstItem(){
        List<Item> itemList = new ArrayList<>();

        itemList = inventory.getAllItems();
        
        try {
            return itemList.get(0);
        } catch (Exception e) {
            System.out.println("Error in retrieving item list: " + e);
        }

        return null;

    }
}
