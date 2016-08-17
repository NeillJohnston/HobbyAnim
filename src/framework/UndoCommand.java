package framework;

/**
 * UndoCommand is a simple interface that can be used as an undoable command.
 * Each class should define their own commands as nested classes, all of which implement UndoCommand.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public interface UndoCommand {

    /**
     * Undo whatever this command does.
     */
    public void undo();

    /**
     * Redo whatever this command does.
     */
    public void execute();

}
