package it.eneaminelli.shopmanagement.savesystem;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import it.eneaminelli.shopmanagement.exceptions.PersistenceServiceException;
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


    // public void save(Inventory inventory, String filePath) throws PersistenceException{
    //     try {
    //         //get data to save and convert collection to json
    //         Collection<Item> itemsToSave = inventory.getAllItems();
    //         String json = gson.toJson(itemsToSave, ITEM_COLLECTION_TYPE);
    
    //         //I/O to write string to file
    //         Path path = Paths.get(filePath);
    //         Files.writeString(path, json);
    //     } catch (IOException e) {
    //         throw new PersistenceException("Failed to save inventory to file: " + filePath, e);
    //     }
    // }

    /**
     * Saves the current state of the inventory to a JSON file.
     *
     * @param inventory The inventory object to save
     * @param filePath  The path to the file where the inventory will be saved
     * @throws PersistenceException if any I/O error occurs
     */
    public void save(Inventory inventory, String filePath) throws PersistenceServiceException{
        logger.info("Attempting to save inventory in: " + filePath);

        try{
            Collection<Item> saveItems = inventory.getAllItems();
            String json = gson.toJson(saveItems, ITEM_COLLECTION_TYPE);
            Path path = Paths.get(filePath);

            //check for directory existence
            if(path.getParent() != null){
                Files.createDirectories(path.getParent());
            }

            Files.writeString(path, json);
            logger.info("Saved " + saveItems.size() +" items.");
        } catch (IOException e) {
            logger.error("A critical I/O error occurred while saving in " + filePath, e);
            throw new PersistenceServiceException("Could not save. Check for file permission or disk space", e);
        }
    }

    // public Inventory load(String filePath) throws PersistenceException{
    //     try {
    //         Path path = Paths.get(filePath);
    //         //check for file existance
    //         if(!Files.exists(path)){
    //             logger.error("Save file not found. Strting with a new empty inventory...");
    //             return new Inventory();
    //         }

    //         String json = Files.readString(path);

    //         Collection<Item> loadedItems = gson.fromJson(json, ITEM_COLLECTION_TYPE); //deserializes gson

    //         return new Inventory(loadedItems);
    //     } catch (Exception e) {
    //         throw new PersistenceException("Failed to load a inventory from file: " + filePath, e);
    //     }
    // }

    public Inventory load(String filePath) throws PersistenceServiceException{
        logger.info("Loading file from: " + filePath);
        Path path = Paths.get(filePath);

        try {
            String json = Files.readString(path);
            Collection<Item> loadedItems = gson.fromJson(json, ITEM_COLLECTION_TYPE);

            if(loadedItems == null){
                logger.warn("Inventory is either empty or null. Creating a new one.");
                return new Inventory();
            }

            logger.info("Loaded " + loadedItems.size() + " items.");
            return new Inventory(loadedItems);

        } catch (NoSuchFileException e) {
            //log for first execution of the program
            logger.warn("No save file found in: '" + filePath + "'. Creating new inventory...\nNo action needed if first execution of the program.");
            return new Inventory();
        } catch (IOException e) {
            logger.error("Could not reach file.", e);
            throw new PersistenceServiceException("Failed to load inventory. File might be broken or corrupt.", e);
        } catch (JsonSyntaxException e){
            logger.error("The inventory in '" + filePath + "' is not valid. Must load JSON file.", e);
            throw new PersistenceServiceException("Failed to load inventory, format is NOT valid.", e);
        }
    }
}
