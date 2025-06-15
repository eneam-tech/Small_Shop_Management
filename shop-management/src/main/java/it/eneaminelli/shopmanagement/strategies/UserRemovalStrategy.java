package it.eneaminelli.shopmanagement.strategies;

import java.util.Scanner;

import it.eneaminelli.shopmanagement.inventory.Inventory;

public class UserRemovalStrategy implements ItemRemovalStrategy {

    private final Inventory inventory;

    public UserRemovalStrategy(Inventory inventory){
        this.inventory = inventory;
    }

    @Override
    public String itemToRemove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n---Remmove an item");

        if(inventory.getAllItems().isEmpty()){
            System.out.println("Inventory is already empty.");
            return null;
        }

        //display inventory for item to delete selection
        System.out.println("Inventory:\n");
        System.out.println(inventory.toString());

        System.out.println("\nEnter the product ID of the item to remove or tipe 'exit' to close removal.");
        String idToRemove = scanner.nextLine();

        if(idToRemove.equalsIgnoreCase("exit")){
            System.out.println("Exiting item cancellation process.");
            return null;
        }

        return idToRemove;
    }
    
}
