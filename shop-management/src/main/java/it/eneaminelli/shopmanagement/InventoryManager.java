package it.eneaminelli.shopmanagement;

public class InventoryManager {
    ItemFactory itemFactory = new ItemFactory();
    Inventory inventory = new Inventory();

    public void createItem(){
        Item test = itemFactory.createItem(ItemType.TEST_ITEM_1, "Test 1", 99.99, "ABX", 9);
        System.out.println(test.toString());
        inventory.addItem(test);
    }
}
