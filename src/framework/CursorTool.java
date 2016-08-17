package framework;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * CursorTool is an enum that provides methods for calling different cursor actions.
 * There should be a value for every possible tool.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public enum CursorTool {

    // --- Enum types ---

    DEFAULT(new TrackCommand() {

        /**
         * ...
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        @Override
        public void constructWithTrack(ArrayList<Point> track) {}

        /**
         * ...
         */
        @Override
        public void undo() {}

        /**
         * ...
         */
        @Override
        public void execute() {}

    }),
    LINE(new TrackCommand() {

        /**
         *
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        @Override
        public void constructWithTrack(ArrayList<Point> track) {

            // TODO Store information for this line.

        }

        /**
         *
         */
        @Override
        public void undo() {

            // TODO Destroy created line.

        }

        /**
         *
         */
        @Override
        public void execute() {

            // TODO Recreate line.

        }

    }),
    DEBUG(new TrackCommand() {
        /**
         *
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        @Override
        public void constructWithTrack(ArrayList<Point> track) {}

        /**
         *
         */
        @Override
        public void undo() {}

        /**
         *
         */
        @Override
        public void execute() { System.out.println("Command executed."); }
    })
    ;

    // --- Construction and use of enum ---

    /**
     * Commands used by this cursor tool from the constructor.
     */
    private TrackCommand execOnRelease;

    /**
     * Stroke track technology, will be null unless tracking is being performed.
     */
    private ArrayList<Point> track;

    /**
     * Construct a cursor.
     *
     * @param execOnRelease     UndoCommand to be created when the mouse is released after a performing a stroke
     */
    CursorTool(TrackCommand execOnRelease) {

        this.execOnRelease = execOnRelease;
        this.track = null;

    }

    /**
     * When the mouse is pressed down, begin tracking a mouse stroke.
     * In order to fit the command pattern but also make a sensible UI, a "temporary" action will be performed while
     * tracking, until the mouse is released, when the execOnRelease command can be used.
     * The points the mouse passes through is called the "track" of the stroke.
     *
     * @param e     MouseEvent to use
     */
    public void startTrack(MouseEvent e) {

        if(track == null) {

            track = new ArrayList<>();
            track.add(e.getPoint());

        }

    }

    /**
     * Update the temporary action while the stroke is being created.
     *
     * @param e     MouseEvent to use
     */
    public void updateTrack(MouseEvent e) {

        if(track != null) {

            track.add(e.getPoint());

        }

    }

    /**
     * When the mouse is released, save the action performed in the UndoManager.
     *
     * @param e     MouseEvent to use
     */
    public void endTrack(MouseEvent e) {

        track.add(e.getPoint());
        execOnRelease.constructWithTrack(track);
        execOnRelease.execute();
        HobbyAnim.undoManager.add(execOnRelease);

    }

    // --- Helper interface TrackCommand ---

    /**
     * TrackCommand is an extension of UndoCommand that allows extra data to be used.
     * The new method, constructWithTrack, takes the track ArrayList as an argument.
     */
    private interface TrackCommand extends UndoCommand {

        /**
         * TrackCommand exclusively is used by CursorTool, which is used to track the mouse cursor's strokes.
         * ConstructWithTrack takes this tracking information as an argument and uses that to
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        public void constructWithTrack(ArrayList<Point> track);

    }

    // --- Helper interface Tracker ---

    /**
     * Tracker is an object that follows the cursor and updates when it moves.
     */
    private interface Tracker {

        /**
         * Same tracking methods as in CursorTool.
         *
         * @param p
         */
        public void startTrack(Point p);
        public void updateTrack(Point p);
        public void endTrack(Point p);

    }

}
