package it.eneaminelli.shopmanagement.strategies;

import java.util.Scanner;

import it.eneaminelli.shopmanagement.item.Item;

public class UserInputCreationStrategy implements ItemCreationStrategy {
    @Override
    public Item create(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product ID: ");
        String id = scanner.nextLine();
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter price: ");
        double price = scanner.nextDouble();
        System.out.println("Enter stock: ");
        int stock = scanner.nextInt();
        //TODO: add itemtype/ofType selection

        return new Item.ItemBuilder(id, name).withPrice(price).withStock(stock).build();
    }
}
