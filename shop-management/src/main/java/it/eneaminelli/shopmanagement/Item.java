package it.eneaminelli.shopmanagement;

public class Item implements ItemInterface {
    private String name;
    private double price;
    private String productID;
    private int stock;
    private ItemType itemType;

    public Item(String name, double price, String productID, int stock, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.productID = productID;
        this.stock = stock;
        this.itemType = itemType;
    }

    @Override
    public String toString(){
        return "Item characteristics:\n - Name: " + name + "\n - Price: " + price + "\n - Stock: " + stock + "\n - Serial: " + productID + "\n - Item type: " + itemType;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
