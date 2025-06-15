package it.eneaminelli.shopmanagement.commands;

import it.eneaminelli.shopmanagement.inventory.Inventory;
import it.eneaminelli.shopmanagement.item.Item;

public class RemoveCommand implements Command {

    private final Inventory inventory;
    private final String removedItemID;
    private Item removedItem;

    public RemoveCommand(Inventory inventory, String removedItemID){
        this.inventory = inventory;
        this.removedItemID = removedItemID;
    }

    @Override
    public void execute() {
        this.removedItem = inventory.getItem(removedItemID);
        if(this.removedItem != null){
            inventory.removeItem(removedItemID);
        }
    }

    @Override
    public void undo() {
        if(this.removedItem != null){
            inventory.addItem(removedItem);
        }
    }
    
}
