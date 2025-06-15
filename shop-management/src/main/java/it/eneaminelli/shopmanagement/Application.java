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
import it.eneaminelli.shopmanagement.strategies.UserRemovalStrategy;

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
                handleRemoveItem();
                break;
            case "4":
                handleUndo();
                break;
            case "5":
                handleRedo();
                break;
            case "6":
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

    private void handleRemoveItem(){
        manager.removeItem(new UserRemovalStrategy(manager.getInventory()));
    }

    private void handleUndo(){
        System.out.println("---Undo last action");
        manager.undo();
    }

    private void handleRedo(){
        System.out.println("---Redo last action");
        manager.redo();
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
        System.out.println("3. Remove an item");
        System.out.println("4. Undo last action");
        System.out.println("5. Redo last action");
        System.out.println("6. Save and Exit");
        System.out.println("Choose an option: ");
    }

}
