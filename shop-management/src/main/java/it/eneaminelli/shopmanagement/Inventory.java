package it.eneaminelli.shopmanagement;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> itemList;

    public Inventory(){
        itemList = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        System.out.println("Adding item to inventory: \n" + item.toString() + "\n...");
        itemList.add(item);
        System.out.println("Item added in position " + itemList.indexOf(item));
    }

    //Using standard for loop to avoid concurrent modification exception - TODO: improve logic
    public void removeItemWithCode(String productID){
        for (int i = 0; i < itemList.size(); i ++) {
            Item item = itemList.get(i);
            if(item.getProductID() == null ? productID == null : item.getProductID().equals(productID)){
                System.out.println("Removing item: " + item.toString() +  "\nFrom list...");
                itemList.remove(i);
                System.out.println("Item removed successfully!");
            }
        }
    }

    public void removeItem(Item item){
        String productID = item.getProductID();
        removeItemWithCode(productID);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        try {
            if(itemList != null && !itemList.isEmpty()){
                
                for (int i = 0; i < itemList.size(); i ++) {
                    sb.append("\n").append(i + 1).append(". ").append(itemList.get(i).toString());
                }

                System.out.println("Inventory:\n");
                System.out.println(sb.toString());

                return sb.toString();

            } else if (itemList != null && itemList.isEmpty()){
                return "Inventory is empty.";

            } else {
                return "Error in inventory. Inventory is currently null.";

            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Invalid Inventory. Exception occurred: " + e);
        }
        return "Unknown error occurred. Please try again";
    }
}
