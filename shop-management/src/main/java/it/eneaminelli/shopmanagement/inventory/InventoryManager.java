package it.eneaminelli.shopmanagement.inventory;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eneaminelli.shopmanagement.commands.AddCommand;
import it.eneaminelli.shopmanagement.commands.CommandManager;
import it.eneaminelli.shopmanagement.commands.RemoveCommand;
import it.eneaminelli.shopmanagement.item.Item;
import it.eneaminelli.shopmanagement.item.ItemFactory;
import it.eneaminelli.shopmanagement.strategies.ItemCreationStrategy;
import it.eneaminelli.shopmanagement.strategies.ItemRemovalStrategy;

public class InventoryManager {

    private static final Logger logger = LoggerFactory.getLogger(InventoryManager.class);

    private final ItemFactory itemFactory;
    private Inventory inventory;
    private final CommandManager commandManager;

    public InventoryManager(Inventory inventory, ItemFactory itemFactory) {
        this.inventory = inventory;
        this.itemFactory = itemFactory;
        this.commandManager = new CommandManager();
    }

    //Entry point for creating an item - new objects must implement strategy interface!
    public void createItem(ItemCreationStrategy strategy){
        if(strategy == null){
            logger.error("Strategy for adding cannot be null.");
            return;
        }

        //Delegating construction logic to strategies

        Item newItem = strategy.create();

        if(newItem != null) {
            AddCommand command = new AddCommand(inventory, newItem);
            commandManager.executeCommand(command);
            logger.info("Executed AddItem command for: {}", newItem.getName());
        } else {
            logger.error("createItem execution failed.");
            System.err.println("Item creation failed: check the input.");
        }
    }

    //Entry point for deleting an item
    public void removeItem(ItemRemovalStrategy strategy){
        if(strategy == null){
            logger.error("Strategy for removal can't be null.");
            return;
        }

        //delegating removing logic to strategies
        String productIDToRemove = strategy.itemToRemove();
        if(productIDToRemove != null && !productIDToRemove.isBlank()){
            if(inventory.getItem(productIDToRemove) != null){ //checking if item with such id is in inventory
                RemoveCommand command = new RemoveCommand(inventory, productIDToRemove);
                commandManager.executeCommand(command);
                logger.info("Executed RemoveCommand for ID {}", productIDToRemove);
                System.out.println("Item with ID '" + productIDToRemove + "' was removed successfully.");
            } else {
                logger.warn("User tried RemoveCommand for item with ID: {}", productIDToRemove);
                System.err.println("No item with such id found. ID: " + productIDToRemove);
            }
        }
    }

    public void undo(){
        if(commandManager.canUndo()){
            commandManager.undo();
            logger.info("Undo performed");
        } else {
            logger.info("No action to undo");
        }
    }

    public void redo(){
        if(commandManager.canRedo()){
            commandManager.redo();
            logger.info("Redo performed");
        } else {
            logger.info("No action to redo");
        }
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
            logger.error("Error in retrieving item list: " + e);
        }

        return null;

    }
}
