package it.eneaminelli.shopmanagement.commands;

public interface Command {
    void execute();
    void undo();
}
