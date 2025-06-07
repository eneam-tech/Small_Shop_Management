package it.eneaminelli.shopmanagement;

import it.eneaminelli.shopmanagement.inventory.InventoryManager;
import it.eneaminelli.shopmanagement.savesystem.SaveFunctionality;

public class Application {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.createItem();
        System.out.println(inventoryManager.getInventory().toString());
        SaveFunctionality saveFunctionality = new SaveFunctionality();
        saveFunctionality.save(inventoryManager);
    }
}
