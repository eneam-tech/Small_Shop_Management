package it.eneaminelli.shopmanagement;

import it.eneaminelli.shopmanagement.inventory.InventoryManager;

public class Application {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.createItem();
        System.out.println(inventoryManager.getInventory().toString());
    }
}
