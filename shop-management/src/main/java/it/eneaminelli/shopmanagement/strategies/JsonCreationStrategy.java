package it.eneaminelli.shopmanagement.strategies;

//Concrete strategy for cr

import com.google.gson.Gson;

import it.eneaminelli.shopmanagement.item.Item;

public class JsonCreationStrategy implements ItemCreationStrategy {
    private final String json;

    public JsonCreationStrategy(String json){
        this.json = json;
    }

    @Override
    public Item create(){
        //TODO: add deserialization for the data transfer object (for validation)
        //before using the builder
        Gson gson = new Gson();
        //Right now assuming the JSON has the required fields for object creation, 
        //add validation as said before
        Item.ItemBuilder builder = gson.fromJson(json, Item.ItemBuilder.class);
        
        return builder.build();
    }

}
