package it.eneaminelli.shopmanagement.strategies;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import it.eneaminelli.shopmanagement.item.Item;
import it.eneaminelli.shopmanagement.item.ItemType;
import it.eneaminelli.shopmanagement.item.itemsubclasses.ElectronicItem;
import it.eneaminelli.shopmanagement.item.itemsubclasses.PerishableItem;
//TODO: Add input sanitization!!!!!
public class UserInputCreationStrategy implements ItemCreationStrategy {
    Scanner scanner = new Scanner(System.in);

    @Override
    public Item create(){
        try{
            //---- type selection ----
            System.out.println("Select the item type from this list: ");
            for(ItemType type : ItemType.values()){
                System.out.println(" - " + type.name());
            }
            System.out.println("Enter type: ");
            String typeInput = scanner.nextLine().toUpperCase();
            ItemType selectedType = ItemType.valueOf(typeInput); //convert string to enum for item selectionm
            
            //---other details selection ---
            System.out.println("Enter product ID: ");
            String id = scanner.nextLine();
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            System.out.println("Enter price: ");
            double price = scanner.nextDouble();
            System.out.println("Enter stock: ");
            int stock = scanner.nextInt();
            scanner.nextLine(); //leave for lne consumption after int scan before!!
    
            switch (selectedType) {
                case PERISHABLE:
                    System.out.println("Enter expiry date (YYYY-MM-DD):");
                    String dateInput = scanner.nextLine();
                    LocalDate expiryDate = LocalDate.parse(dateInput); //exception if format string is wrong
    
                    return new PerishableItem.PerishableBuilder(id, name).withPrice(price).withStock(stock).withExpiryDate(expiryDate).build();
                case ELECTRONIC:
                    System.err.println("Enter warranty (months): ");
                    int warranty = scanner.nextInt();
                    scanner.nextLine();
    
                    return new ElectronicItem.ElectronicBuilder(id, name).withPrice(price).withStock(stock).withWarranty(warranty).build();
                default:
                    System.out.println("Unsupported type selected.");
                    return null;
            }

        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter number for price, stock and warranty.");
            scanner.nextLine();
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid item type. Please enter one of listed types.");
            return null;
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use YYYY-MM-DD format.");
            return null;
        }        
    }
}
