package it.eneaminelli.shopmanagement.savesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eneaminelli.shopmanagement.inventory.Inventory;
import it.eneaminelli.shopmanagement.item.Item;

public class SaveFunctionality {
    private List<Map<String, String>> propertyMaps = new ArrayList<>();

    private List<Map<String, String>> parseProperties(Inventory inventory){
        List<Item> items = inventory.getItemList();
        for(Item item : items){
            Map<String, String> itemProperties = new HashMap<>();
            itemProperties.put("name", item.getName());
            itemProperties.put("productID", item.getProductID());
            propertyMaps.add(itemProperties);
        }
        return propertyMaps;
    }

    //TODO: import Gson, finish save functionality
    public void save(){
        // Gson gson = new Gson();
        // String json = gson.toJson(propertyMaps);
        // Files.writeString(Path.of("inventory.json"), json); 
    }
}
