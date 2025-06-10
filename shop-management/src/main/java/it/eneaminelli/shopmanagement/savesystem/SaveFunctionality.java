package it.eneaminelli.shopmanagement.savesystem;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import it.eneaminelli.shopmanagement.inventory.InventoryManager;
import it.eneaminelli.shopmanagement.item.Item;


public class SaveFunctionality {
    
    Gson gson = new Gson();
    List<String> savedItems = new ArrayList<>();

    public void save(InventoryManager inventoryManager){
        String jsonString;
        /*
        String jsonString = gson.toJson(inventoryManager.getFirstItem());

        System.out.println("Save test:");
        System.out.println("Object: " + jsonString);
        System.out.println("Commencing serialization...");
        System.out.println("Serialization result:");
        System.out.println("JSON: " + jsonString);


        System.out.println("--------------Deserialization:---------------");
        Item testItem = gson.fromJson(jsonString, Item.class);
        System.out.println("Deserialization result: ");
        System.out.println(testItem.toString());

         */

        for (Item item : inventoryManager.getInventory().getAllItems()) {
            jsonString = gson.toJson(item);
            System.out.println("Serialized item: " + jsonString);
            savedItems.add(jsonString);
        }

        for(String string : savedItems){
            Item testItem = gson.fromJson(string, Item.class);
            System.out.println("Deserialized item: " + testItem.toString());
        }
    }
/*
    public void load(InventoryManager inventoryManager) {
        for(String string : savedItems){
            inventoryManager.createItem();
        }
    }
 */
}
