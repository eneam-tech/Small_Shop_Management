package it.eneaminelli.shopmanagement.item;

import java.time.LocalDate;

import it.eneaminelli.shopmanagement.item.itemsubclasses.ElectronicItem;
import it.eneaminelli.shopmanagement.item.itemsubclasses.PerishableItem;

public class ItemFactory {
    public Item createItem(ItemType type, String productID, String name, double price, int stock, Object... params){
        switch (type) {
            case PERISHABLE:
                if(params.length > 0 && params[0] instanceof LocalDate) {
                    LocalDate expiryDate = (LocalDate) params[0];
                    //Factoery uses builder for object construction
                    return new PerishableItem.PerishableBuilder(productID, name).withPrice(price).withStock(stock).withExpiryDate(expiryDate).build();
                } else {
                    throw new IllegalArgumentException("PerishableItem requires a localdate expiry date.");
                }
            case ELECTRONIC:
                if(params.length > 0 && params[0] instanceof Integer){
                    int warranty = (Integer) params[0];
                    return new ElectronicItem.ElectronicBuilder(productID, name).withPrice(price).withStock(stock).withWarranty(warranty).build();
                } else {
                    throw new IllegalArgumentException("ElectronicItem requires an int warranty (expressed in months).");
                }
            default:
                throw new AssertionError();
        }
    }
}
