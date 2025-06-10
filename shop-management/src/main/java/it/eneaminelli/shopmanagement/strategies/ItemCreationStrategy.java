package it.eneaminelli.shopmanagement.strategies;

import it.eneaminelli.shopmanagement.item.Item;

//Strategy interface for item's creation contract
public interface ItemCreationStrategy {
    Item create();
}
