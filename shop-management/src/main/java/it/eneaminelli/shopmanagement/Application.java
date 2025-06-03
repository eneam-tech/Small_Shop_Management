package it.eneaminelli.shopmanagement;

public class Application {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ItemFactory itemFactory = new ItemFactory();
        Item test = itemFactory.createItem(ItemType.TEST_ITEM_1, "Test 1", 99.99, "ABX", 9);
        System.out.println(test.toString());
    }
}
