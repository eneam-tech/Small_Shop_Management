package it.eneaminelli.shopmanagement;

import java.util.Scanner;

import it.eneaminelli.shopmanagement.inventory.Inventory;
import it.eneaminelli.shopmanagement.inventory.InventoryManager;
import it.eneaminelli.shopmanagement.item.ItemFactory;
import it.eneaminelli.shopmanagement.savesystem.PersistenceException;
import it.eneaminelli.shopmanagement.savesystem.PersistenceService;
import it.eneaminelli.shopmanagement.strategies.UserInputCreationStrategy;

public class Application {
    public static final String SAVE_FILE_PATH = "inventory.json";

    public static void main(String[] args) {
        //--- Setup
        PersistenceService persistenceService = new PersistenceService();
        Inventory inventory;

        //Try loading
        try{
            inventory = persistenceService.load(SAVE_FILE_PATH);
        } catch (PersistenceException e) {
            System.err.println("FATAL ERROR: could not load inventory.\nExiting with error: " + e);
            return;
        }

        InventoryManager manager = new InventoryManager(inventory, new ItemFactory());
        Scanner scannerMenu = new Scanner(System.in);

        while (true) { 
            System.out.println("\n+++ SHOP MANAGEMENT SOFTWARE +++");
            System.out.println("1. Add a new item");
            System.out.println("2. Display inventory");
            System.out.println("3. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scannerMenu.nextLine();

            switch (choice) {
                case "1":
                    // Use the UserInputCreationStrategy to create an item
                    System.out.println("--- Starting Item Creation ---");
                    manager.createItem(new UserInputCreationStrategy());
                    break;
                case "2":
                    // Display the current inventory state
                    System.out.println("\n--- Current Inventory ---");
                    System.out.println(inventory.toString());
                    break;
                case "3":
                    // Save the inventory and exit the application
                    try {
                        System.out.println("Saving inventory...");
                        persistenceService.save(inventory, SAVE_FILE_PATH);
                        System.out.println("Save successful. Exiting.");
                        return; // Exit the main method, thus ending the program
                    } catch (PersistenceException e) {
                        System.err.println("CRITICAL ERROR: Could not save inventory! " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
