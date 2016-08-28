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

        stackIndex++;
        // If not at the end of the stack, trim the top of the stack and then add the new UndoCommand.
        if(stackIndex < stack.size()) stack.removeAll(stack.subList(stackIndex, stack.size()));
        stack.add(uc);

    }

    /**
     * Undo the last command.
     */
    public void undo() {

        // Make sure the stack has something to undo.
        try {

            stack.get(stackIndex).undo();
            stackIndex--;
            HobbyAnim.canvas.repaint();

        } catch(ArrayIndexOutOfBoundsException e) {

            System.err.println("Attempting to undo with a blank stack.");

        }

    }

    /**
     * Redo the next command.
     */
    public void redo() {

        // Make sure the stack has something to redo.
        try {

            stack.get(stackIndex + 1).execute();
            stackIndex++;
            HobbyAnim.canvas.repaint();

        } catch(IndexOutOfBoundsException e) {

            System.err.println("Attempting to redo at end of stack.");

        }

    }

}
