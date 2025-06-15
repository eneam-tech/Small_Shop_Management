package it.eneaminelli.shopmanagement.commands;

import it.eneaminelli.shopmanagement.inventory.Inventory;
import it.eneaminelli.shopmanagement.item.Item;

public class AddCommand implements Command {

    private final Inventory inventory;
    private final Item itemToAdd;

    public AddCommand(Inventory inventory, Item itemToAdd){
        this.inventory = inventory;
        this.itemToAdd = itemToAdd;
    }

    @Override
    public void execute() {
        inventory.addItem(itemToAdd);
    }

    @Override
    public void undo() {
        inventory.removeItem(itemToAdd.getProductID());
    }
    
}
