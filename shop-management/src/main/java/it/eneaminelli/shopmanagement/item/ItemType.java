package it.eneaminelli.shopmanagement.item;

public enum ItemType {
    PERISHABLE,
    ELECTRONIC,
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
            case PERISHABLE -> "Perishable";
            case ELECTRONIC -> "Electronic";
            default -> "Error in type input.";
        };
    }

}
