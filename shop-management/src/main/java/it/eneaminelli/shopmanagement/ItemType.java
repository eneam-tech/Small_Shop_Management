package it.eneaminelli.shopmanagement;

public enum ItemType {
    TEST_ITEM_1,
    TEST_ITEM_2,
    DEFAULT_ITEM;

    public static String getType(){
        StringBuilder sb = new StringBuilder();
        for (ItemType type : ItemType.values()){
            sb.append("- ").append(type.toString()).append("\n");
        }
        return sb.toString();
    }   

    public String toString(){
        return switch (this) {
            case TEST_ITEM_1 -> "test item 1";
            case TEST_ITEM_2 -> "test item 2";
            case DEFAULT_ITEM -> "default item";
            default -> "Error in type input.";
        };
    }

}
