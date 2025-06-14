package it.eneaminelli.shopmanagement;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eneaminelli.shopmanagement.inventory.Inventory;
import it.eneaminelli.shopmanagement.inventory.InventoryManager;
import it.eneaminelli.shopmanagement.item.ItemFactory;
import it.eneaminelli.shopmanagement.savesystem.PersistenceException;
import it.eneaminelli.shopmanagement.savesystem.PersistenceService;
import it.eneaminelli.shopmanagement.strategies.UserInputCreationStrategy;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static final String SAVE_FILE_PATH = "inventory.json";

    public static void main(String[] args) {
        //--- Setup
        PersistenceService persistenceService = new PersistenceService();
        Inventory inventory;

        //Try loading
        try{
            inventory = persistenceService.load(SAVE_FILE_PATH);
        } catch (PersistenceException e) {
            logger.error("FATAL ERROR: could not load inventory.\nExiting with error: " + e);
            return;
        }

        InventoryManager manager = new InventoryManager(inventory, new ItemFactory());
        Scanner scannerMenu = new Scanner(System.in);

        while (true) { 
            logger.info("\n+++ SHOP MANAGEMENT SOFTWARE +++");
            logger.info("1. Add a new item");
            logger.info("2. Display inventory");
            logger.info("3. Save and Exit");
            logger.info("Choose an option: ");

            String choice = scannerMenu.nextLine();

            switch (choice) {
                case "1":
                    // Use the UserInputCreationStrategy to create an item
                    logger.info("--- Starting Item Creation ---");
                    manager.createItem(new UserInputCreationStrategy());
                    break;
                case "2":
                    // Display the current inventory state
                    logger.info("\n--- Current Inventory ---");
                    logger.info(inventory.toString());
                    break;
                case "3":
                    // Save the inventory and exit the application
                    try {
                        logger.info("Saving inventory...");
                        persistenceService.save(inventory, SAVE_FILE_PATH);
                        logger.info("Save successful. Exiting.");
                        return; // Exit the main method, thus ending the program
                    } catch (PersistenceException e) {
                        logger.error("CRITICAL ERROR: Could not save inventory! " + e.getMessage());
                    }
                    break;
                default:
                    logger.debug("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
