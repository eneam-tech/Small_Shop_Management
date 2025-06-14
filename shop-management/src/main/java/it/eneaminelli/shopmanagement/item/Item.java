package it.eneaminelli.shopmanagement.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eneaminelli.shopmanagement.item.itemsubclasses.ElectronicItem;

public abstract class Item implements ItemInterface {

    private static final Logger logger = LoggerFactory.getLogger(Item.class);

    private final String name;
    private final double price;
    private final String productID;
    private int stock;

    // generic <?> because base class don't need to know specific builder type
    protected Item(ItemBuilder<?, ?> builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.productID = builder.productID;
        this.stock = builder.stock;
    }

    // -----------------------> Getters and setters <----------------------------
    @Override
    public String toString(){
        return "Item characteristics:\n - Name: " + name + "\n - Price: " + price + "\n - Stock: " + stock + "\n - Serial: " + productID;
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

    public String getProductID() {
        return productID;
    }
    // -------------------> End of Getters and setters <------------------------

    //Builder pattern for personalized item creation
    //T is the type of item to build (e.g. ElectronicItem) TODO: Add subclasses for item types
    //B is the type of the itembuilder itsel (e.g. ElectronicItem.ItemBUilder)
    public abstract static class ItemBuilder<T extends Item, B extends ItemBuilder<T, B>>{
        //Fields for all builders
        private String name;
        private double price;
        private String productID;
        private int stock;
        
        public ItemBuilder(String productID, String name) {
            this.productID = productID;
            this.name = name;
        }

        public B withPrice(double price){
            this.price = price;
            return self();
        }

        public B withStock(int stock){
            this.stock = stock;
            return self();
        }

        //Build method is abstrac - remember to implement for each concrete builder
        public abstract T build();
        
        //Hlper method to cast 'this' to corret builder type
        protected abstract B self();
        
    }

}
