package framework;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
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

    DEFAULT(new Tracker() {

        /**
         *
         *
         * @param p     Point2D object to track
         */
        @Override
        public void startTrack(Point2D p) {}

        /**
         *
         *
         * @param p
         */
        @Override
        public void updateTrack(Point2D p) {}

        /**
         *
         *
         * @param p
         */
        @Override
        public void endTrack(Point2D p) {}

    }, new TrackCommand() {

        /**
         * ...
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        @Override
        public void constructWithTrack(ArrayList<Point2D> track) {}

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
    LINE(new Tracker() {

        /**
         *
         *
         * @param p     Point2D object to track
         */
        @Override
        public void startTrack(Point2D p) {}

        /**
         *
         *
         * @param p
         */
        @Override
        public void updateTrack(Point2D p) {}

        /**
         *
         *
         * @param p
         */
        @Override
        public void endTrack(Point2D p) {}

    }, new TrackCommand() {

        /**
         *
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        @Override
        public void constructWithTrack(ArrayList<Point2D> track) {

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
    DEBUG(new Tracker() {

        /**
         *
         *
         * @param p     Point2D object to track
         */
        @Override
        public void startTrack(Point2D p) {}

        /**
         *
         *
         * @param p
         */
        @Override
        public void updateTrack(Point2D p) {}

        /**
         *
         *
         * @param p
         */
        @Override
        public void endTrack(Point2D p) {}

    }, new TrackCommand() {
        /**
         *
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        @Override
        public void constructWithTrack(ArrayList<Point2D> track) {}

        /**
         *
         */
        @Override
        public void undo() {}

        /**
         *
         */
        @Override
        public void execute() {

            System.out.println("Command executed.");

        }

    }),
    ;

    // --- Construction and use of enum ---

    /**
     * Commands used by this cursor tool from the constructor.
     */
    private Tracker cursorTracker;
    private TrackCommand execOnRelease;

    /**
     * Stroke track technology, will be null unless tracking is being performed.
     */
    private ArrayList<Point2D> track;

    /**
     * Construct a cursor.
     *
     * @param execOnRelease     UndoCommand to be created when the mouse is released after a performing a stroke
     */
    CursorTool(Tracker cursorTracker, TrackCommand execOnRelease) {

        this.cursorTracker = cursorTracker;
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
        public void constructWithTrack(ArrayList<Point2D> track);

    }

    // --- Helper class Tracker ---

    /**
     * Tracker is an object that follows the cursor and updates when it moves.
     */
    private interface Tracker {

        /**
         * Tracking methods.
         *
         * @param p     Point2D object to track
         */
        public void startTrack(Point2D p);
        public void updateTrack(Point2D p);
        public void endTrack(Point2D p);

    }

}
