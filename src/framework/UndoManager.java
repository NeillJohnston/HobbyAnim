package framework;

import java.util.ArrayList;

/**
 * UndoManager manages a stack of UndoCommand objects.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class UndoManager {

    /**
     * The list of UndoCommands and current index in the list.
     */
    private ArrayList<UndoCommand> stack;
    private int stackIndex;

    /**
     * Construct UndoManager by initializing default variables.
     */
    public UndoManager() {

        stack = new ArrayList<>();
        stackIndex = -1;

    }

    /**
     * Add a new UndoCommand to the stack.
     * If the stackIndex is not at the end of the stack, all future commands will be removed.
     * The command is then added and the stackIndex is updated as usual.
     *
     * @param uc    UndoCommand to add.
     */
    public void add(UndoCommand uc) {

        if(stackIndex != stack.size() - 1)

            stack = (ArrayList<UndoCommand>) stack.subList(0, stackIndex);

        stack.add(uc);
        stackIndex++;

    }

    /**
     * Undo the last command.
     */
    public void undo() {

        // Make sure the stack has something to undo.
        if(stack.get(stackIndex) != null) {

            stack.get(stackIndex).undo();
            stackIndex--;

        }

    }

    /**
     * Redo the next command.
     */
    public void redo() {

        // Make sure the stack has something to redo.
        if(stack.get(stackIndex + 1) != null) {

            stackIndex++;
            stack.get(stackIndex).execute();

        }

    }

}
