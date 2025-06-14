package it.eneaminelli.shopmanagement.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eneaminelli.shopmanagement.item.Item;

public class Inventory {

    private static final Logger logger = LoggerFactory.getLogger(Inventory.class);

    //TODO: convert from list (O(n) complexity) to map (o(1)) complexity
    private final Map<String, Item> inventoryMap;

    //Constructor for new sessions
    public Inventory(){
        this.inventoryMap = new HashMap<>();
    }

    //Constructor for resuming session
    public Inventory(Collection<Item> initialItems){
        this.inventoryMap = initialItems.stream().collect(Collectors.toMap(Item -> Item.getProductID(), Function.identity(), (item1, item2) -> item2));
    }

    public void addItem(Item item){
        if(item == null || item.getProductID() == null){
            logger.error("Cannot add a null item or item without an ID");
            return;
        } 

        logger.info("Adding/updating item with ID: " + item.getProductID());
        inventoryMap.put(item.getProductID(), item);
        logger.info("Operation successful");
    }

    public Item getItem(String productID){
        return inventoryMap.get(productID);
    }

    //TODO: implement commander pattern
    public void removeItem(String productID){
        Item removedItem = inventoryMap.remove(productID);
        if(removedItem != null){
            logger.info("Succesfully removed item " + removedItem.getName());
        } else {
            logger.info("No item found with ID: " + productID);
        }
    }

    //Return list for other methods
    public List<Item> getAllItems(){
        return new ArrayList<>(inventoryMap.values());
    }


    @Override
    public String toString(){
        if(inventoryMap.isEmpty()){
            return "inventory is empty.";
        }

        StringBuilder sb = new StringBuilder();
        int i = 1;
        for(Item item : inventoryMap.values()){
            sb.append(i++).append(". ").append(item.toString()).append("\n");
        }
        return sb.toString();
    }

}
