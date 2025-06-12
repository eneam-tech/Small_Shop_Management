package it.eneaminelli.shopmanagement.item.itemsubclasses;

import it.eneaminelli.shopmanagement.item.Item;

public class ElectronicItem extends Item {
    private final int warranty; //in months

    protected ElectronicItem(ElectronicBuilder builder){
        super(builder);
        this.warranty = builder.warranty;
    }

    public int getWarranty(){
        return warranty;
    }

    @Override
    public String toString(){
        return "Item characteristics (Electronic):\n" + super.toString() + "\n - Warranty: " + warranty + " months";
    }

    //concrete builder for electronic item
    public static class ElectronicBuilder extends Item.ItemBuilder<ElectronicItem, ElectronicBuilder>{
        private int warranty;
        public ElectronicBuilder(String productID, String name){
            super(productID, name);
        }

        //Specifics for electronic item
        public ElectronicBuilder withWarranty(int warranty){
            this.warranty = warranty;
            return this;
        }

        //abstract method implementation
        @Override
        public ElectronicItem build(){
            return new ElectronicItem(this);
        }

        @Override
        protected ElectronicBuilder self(){
            return this;
        }
    }
}
