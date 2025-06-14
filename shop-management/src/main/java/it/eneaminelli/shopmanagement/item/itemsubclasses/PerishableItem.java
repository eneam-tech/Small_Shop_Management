package it.eneaminelli.shopmanagement.item.itemsubclasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eneaminelli.shopmanagement.item.Item;

public class PerishableItem extends Item {
    
    private static final Logger logger = LoggerFactory.getLogger(PerishableItem.class);

    private final LocalDate expiryDate;

    protected PerishableItem(PerishableBuilder builder) {
        super(builder);
        this.expiryDate = builder.expiryDate;
    }

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    @Override
    public String toString(){
        return "Item characteristics (Perishable):\n" + super.toString() + "\n - Expiry date: " + expiryDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    //concrete builder for perishable item
    public static class PerishableBuilder extends Item.ItemBuilder<PerishableItem, PerishableBuilder>{

        private LocalDate expiryDate;
        public PerishableBuilder(String productID, String name) {
            super(productID, name);
        }

        //Specific for perishable item
        public PerishableBuilder withExpiryDate(LocalDate expiryDate){
            this.expiryDate = expiryDate;
            return this;
        }

        //Implement abstract methods
        @Override
        public PerishableItem build(){
            return new PerishableItem(this);
        }

        @Override
        protected PerishableBuilder self() {
            return this;
        }
    }

}
