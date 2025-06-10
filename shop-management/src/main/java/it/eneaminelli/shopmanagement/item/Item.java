package it.eneaminelli.shopmanagement.item;

public class Item implements ItemInterface {
    private final String name;
    private final double price;
    private final String productID;
    private final ItemType itemType;
    private int stock;

    private Item(ItemBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.productID = builder.productID;
        this.stock = builder.stock;
        this.itemType = builder.itemType;
    }

    // -----------------------> Getters and setters <----------------------------
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

    public String getProductID() {
        return productID;
    }

    public String prepareForSaving(){
        return name + productID + itemType.toString(); 
    }

    // -------------------> End of Getters and setters <------------------------

    //Builder pattern for personalized item creation
    public static class ItemBuilder{
        private String name;
        private double price;
        private String productID;
        private int stock;
        private ItemType itemType;
        
        public ItemBuilder(String productID, String name) {
            this.productID = productID;
            this.name = name;
        }

        public ItemBuilder withPrice(double price){
            this.price = price;
            return this;
        }

        public ItemBuilder withStock(int stock){
            this.stock = stock;
            return this;
        }

        public ItemBuilder ofType(ItemType itemType){
            this.itemType = itemType;
            return this;
        }

        //Builde method for immutable object
        public Item build(){
            if(name == null || productID == null) {
                throw new IllegalStateException("Name and ProductID cannot be null.");
            }

            return new Item(this);
        }
        
    }

}
