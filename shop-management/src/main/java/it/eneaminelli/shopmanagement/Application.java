package it.eneaminelli.shopmanagement;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eneaminelli.shopmanagement.exceptions.PersistenceServiceException;
import it.eneaminelli.shopmanagement.inventory.Inventory;
import it.eneaminelli.shopmanagement.inventory.InventoryManager;
import it.eneaminelli.shopmanagement.item.ItemFactory;
import it.eneaminelli.shopmanagement.savesystem.PersistenceService;
import it.eneaminelli.shopmanagement.strategies.UserInputCreationStrategy;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String SAVE_FILE_PATH = "inventory.json";

    private final PersistenceService persistenceService;
    private final InventoryManager manager;
    private boolean executeProgram = true;

    public Application() {
        this.persistenceService = new PersistenceService();
        Inventory inventory = loadInventoryOnStartup();
        this.manager = new InventoryManager(inventory, new ItemFactory());
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run(){
        logger.info("+++SHOP MANAGER SOFTWARE STARTED+++");

        try(Scanner scanner = new Scanner(System.in)) {
            while (executeProgram) {
                displayMenu();
                String choice = scanner.nextLine();
                handleMenuChoice(choice);
            }
        }
        logger.info("+++SHOP MANAGER SOFTWARE CLOSED+++");
    }

    public void handleMenuChoice(String choice){
        switch (choice) {
            case "1":
                handleAddItem();
                break;
            case "2":
                handleDisplayInventory();
                break;
            case "3":
                handleSaveExit();
                break;
            default:
                System.out.println("Invalid option, try again.");
                break;
        }
    }


    private void handleAddItem(){
        System.out.println("\n---Creating item...");
        manager.createItem(new UserInputCreationStrategy());
        System.out.println("Item successfully added to inventory.");
    }

    private void handleDisplayInventory(){
        System.out.println("\n---Showing inventory...");
        System.out.println(manager.getInventory().toString());
    }

    private void handleSaveExit(){
        try {
            logger.info("Saving inventory...");
            persistenceService.save(manager.getInventory(), SAVE_FILE_PATH);
            logger.info("Inventory saved.\nExiting...");
            this.executeProgram = false;
        } catch (PersistenceServiceException e) {
            logger.error("CRITICAL ERROR: inventory was not saved.", e);
            System.out.println("ERROR: could not save data. please check log for details.");
        }
    }

    private Inventory loadInventoryOnStartup(){
        try {
            return persistenceService.load(SAVE_FILE_PATH);
        } catch (PersistenceServiceException e) {
            logger.error("A critical error occurred while loading inventory. Cannot launch app.", e);
            System.err.println("FATAL ERROR: could not load inventory. Exiting program...");
            System.exit(1);
            return null;
        }
    }

    private void displayMenu() {
        System.out.println("\n+++SHOP MANAGEMENT SOFTWARE+++");
        System.out.println("1. Add new item");
        System.out.println("2. Display inventory");
        System.out.println("3. Save and exit");
        System.out.println("Choose an option: ");
    }

    // private static final Logger logger = LoggerFactory.getLogger(Application.class);

    // public static final String SAVE_FILE_PATH = "inventory.json";

    // public static void main(String[] args) {
    //     //--- Setup
    //     PersistenceService persistenceService = new PersistenceService();
    //     Inventory inventory;

    //     //Try loading
    //     try{
    //         inventory = persistenceService.load(SAVE_FILE_PATH);
    //     } catch (PersistenceException e) {
    //         logger.error("FATAL ERROR: could not load inventory.\nExiting with error: " + e);
    //         return;
    //     }

    //     InventoryManager manager = new InventoryManager(inventory, new ItemFactory());
    //     Scanner scannerMenu = new Scanner(System.in);

    //     while (true) { 
    //         logger.info("\n+++ SHOP MANAGEMENT SOFTWARE +++");
    //         logger.info("1. Add a new item");
    //         logger.info("2. Display inventory");
    //         logger.info("3. Save and Exit");
    //         logger.info("Choose an option: ");

    //         String choice = scannerMenu.nextLine();

    //         switch (choice) {
    //             case "1":
    //                 // Use the UserInputCreationStrategy to create an item
    //                 logger.info("--- Starting Item Creation ---");
    //                 manager.createItem(new UserInputCreationStrategy());
    //                 break;
    //             case "2":
    //                 // Display the current inventory state
    //                 logger.info("\n--- Current Inventory ---");
    //                 logger.info(inventory.toString());
    //                 break;
    //             case "3":
    //                 // Save the inventory and exit the application
    //                 try {
    //                     logger.info("Saving inventory...");
    //                     persistenceService.save(inventory, SAVE_FILE_PATH);
    //                     logger.info("Save successful. Exiting.");
    //                     return; // Exit the main method, thus ending the program
    //                 } catch (PersistenceException e) {
    //                     logger.error("CRITICAL ERROR: Could not save inventory! " + e.getMessage());
    //                 }
    //                 break;
    //             default:
    //                 logger.debug("Invalid option. Please try again.");
    //                 break;
    //         }
    //     }
    // }
}
