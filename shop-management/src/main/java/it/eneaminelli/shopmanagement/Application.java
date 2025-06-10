package it.eneaminelli.shopmanagement;

import it.eneaminelli.shopmanagement.inventory.InventoryManager;
import it.eneaminelli.shopmanagement.strategies.JsonCreationStrategy;
import it.eneaminelli.shopmanagement.strategies.UserInputCreationStrategy;

public class Application {

    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();

        //Test 1: create item from JSON string (-> loading from file)
        System.out.println("---Creating item from JSON---");
        String jsonFromFile = "{\"productID\":\"XYZ-123\", \"name\":\"Super Widget\", \"price\":19.95, \"stock\":100}";
        inventoryManager.createItem(new JsonCreationStrategy(jsonFromFile));

        System.out.println("\n---------------------------------------\n");

        //Test 2: create item from user input
        System.out.println("---Creating item from user input---");
        inventoryManager.createItem(new UserInputCreationStrategy());

        System.out.println("\nInventory:");
        System.out.println(inventoryManager.getInventory().toString());
    }
}
