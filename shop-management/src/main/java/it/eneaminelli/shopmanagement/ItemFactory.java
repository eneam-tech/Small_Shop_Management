package it.eneaminelli.shopmanagement;

public class ItemFactory {
    public Item createItem(ItemType itemType, String name, double price, String productID, int stock){
        switch (itemType) {
            case TEST_ITEM_1 -> {
                return new Item(name, price, productID, stock, itemType);
            }
            case TEST_ITEM_2 -> {
                return new Item(name, price, productID, stock, itemType);
            }
            default -> throw new IllegalArgumentException("Unknown item type: " + itemType);
        }
    }
}
