package it.eneaminelli.shopmanagement.strategies;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eneaminelli.shopmanagement.item.Item;
import it.eneaminelli.shopmanagement.item.ItemType;
import it.eneaminelli.shopmanagement.item.itemsubclasses.ElectronicItem;
import it.eneaminelli.shopmanagement.item.itemsubclasses.PerishableItem;
//TODO: Add input sanitization!!!!!
public class UserInputCreationStrategy implements ItemCreationStrategy {

    private static final Logger logger = LoggerFactory.getLogger(UserInputCreationStrategy.class);

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
                    System.out.println("Enter warranty (months): ");
                    int warranty = scanner.nextInt();
                    scanner.nextLine();
    
                    return new ElectronicItem.ElectronicBuilder(id, name).withPrice(price).withStock(stock).withWarranty(warranty).build();
                default:
                    logger.debug("Unsupported type selected.");
                    return null;
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter number for price, stock and warranty.");
            logger.error("Invalid input for price, stock or warranty.", e);
            // Clear the invalid input
            scanner.nextLine();
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid item type. Please enter one of listed types.");
            logger.error("Invalid item type selected: ", e);
            return null;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            logger.error("Invalid date format for expiry date: ", e);
            return null;
        }        
    }
}
