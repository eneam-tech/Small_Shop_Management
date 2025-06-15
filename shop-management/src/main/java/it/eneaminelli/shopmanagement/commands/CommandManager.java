package it.eneaminelli.shopmanagement.commands;

import java.util.Stack;

public class CommandManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void executeCommand(Command command){
        command.execute();
        undoStack.push(command);
        redoStack.clear(); //clearing history when new action is done
    }

    public boolean canUndo(){
        return !undoStack.isEmpty();
    }

    public void undo(){
        if(!canUndo()) {
            return;
        }

        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);

    }

    public boolean canRedo(){
        return !redoStack.isEmpty();
    }

    public void redo(){
        if(!canRedo()){
            return;
        }

        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }
}
