package it.eneaminelli.shopmanagement.savesystem;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import it.eneaminelli.shopmanagement.inventory.Inventory;
import it.eneaminelli.shopmanagement.item.Item;
import it.eneaminelli.shopmanagement.item.itemsubclasses.ElectronicItem;
import it.eneaminelli.shopmanagement.item.itemsubclasses.PerishableItem;


//handling of saving and loading functionalities
public class PersistenceService {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceService.class);

    private final Gson gson;
    private static final Type ITEM_COLLECTION_TYPE = new TypeToken<Collection<Item>>() {}.getType();

    //Gson configuration for handling multiple classes thanks to shop-management/src/main/java/com/google/gson/typeadapters/RuntimeTypeAdapterFactory.java
    public PersistenceService(){
        RuntimeTypeAdapterFactory<Item> adapter = RuntimeTypeAdapterFactory.of(Item.class, "Type").registerSubtype(PerishableItem.class, "Perishable").registerSubtype(ElectronicItem.class, "Electronic");
        // GsonBuilder creates coonfiguted gson instanc
        this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create(); //.setPrettyPrinting() makes the saved JSON file human-readable
    }

    /**
     * Saves the current state of the inventory to a JSON file.
     *
     * @param inventory The inventory object to save.
     * @param filePath  The path to the file where the inventory will be saved.
     * @throws PersistenceException if any I/O error occurs.
     */
    public void save(Inventory inventory, String filePath) throws PersistenceException{
        try {
            //get data to save and convert collection to json
            Collection<Item> itemsToSave = inventory.getAllItems();
            String json = gson.toJson(itemsToSave, ITEM_COLLECTION_TYPE);
    
            //I/O to write string to file
            Path path = Paths.get(filePath);
            Files.writeString(path, json);
        } catch (IOException e) {
            throw new PersistenceException("Failed to save inventory to file: " + filePath, e);
        }
    }

    public Inventory load(String filePath) throws PersistenceException{
        try {
            Path path = Paths.get(filePath);
            //check for file existance
            if(!Files.exists(path)){
                logger.error("Save file not found. Strting with a new empty inventory...");
                return new Inventory();
            }

            String json = Files.readString(path);

            Collection<Item> loadedItems = gson.fromJson(json, ITEM_COLLECTION_TYPE); //deserializes gson

            return new Inventory(loadedItems);
        } catch (Exception e) {
            throw new PersistenceException("Failed to load a inventory from file: " + filePath, e);
        }
    }
}
