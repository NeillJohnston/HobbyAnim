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
public abstract class CursorTool {

    /**
     * Possible cursor tools that can be used.
     */
    public static enum TOOLS {
        DEFAULT(),
        LINE(),
        DEBUG(),
    }

    /**
     * Stroke track technology, will be null unless tracking is being performed.
     */
    protected ArrayList<Point2D> track;

    /**
     * When the mouse is pressed down, begin tracking a mouse stroke.
     * In order to fit the command pattern but also make a sensible UI, a "temporary" action will be performed while
     * tracking, until the mouse is released, when the execOnRelease command can be used.
     * The points the mouse passes through is called the "track" of the stroke.
     *
     * @param e     MouseEvent to use
     */
    public void startTrack(MouseEvent e) {

        Point2D p = e.getPoint();

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

        Point2D p = e.getPoint();

        if(track != null) {

            track.add(p);

        }

    }

    /**
     * When the mouse is released, save the action performed in the UndoManager.
     *
     * @param e     MouseEvent to use
     */
    public void endTrack(MouseEvent e) {

        Point2D p = e.getPoint();

        track.add(p);
        TrackCommand execOnRelease = createCommand();
        execOnRelease.constructWithTrack(track);
        try { execOnRelease.execute(); }
        catch(NullPointerException err) { System.out.print("Cannot draw on non-existent frame\n"); }
        HobbyAnim.undoManager.add(execOnRelease);

        track = null;

    }

    /**
     * Create a new command to execute.
     *
     * @return a new TrackCommand
     */
    protected TrackCommand createCommand() {

        // Fill in new TrackCommand here.
        return null;

    }

    /**
     * Paint whatever is being edited by the cursor.
     *
     * @param g2d   Graphics2D object to paint with
     */
    public void paint(Graphics2D g2d) {

        // Fill in with custom code.

    }

    // --- Helper interface TrackCommand ---

    /**
     * TrackCommand is an extension of UndoCommand that allows extra data to be used.
     * The new method, constructWithTrack, takes the track ArrayList as an argument.
     */
    public interface TrackCommand extends UndoCommand {

        /**
         * TrackCommand exclusively is used by CursorTool, which is used to track the mouse cursor's strokes.
         * ConstructWithTrack takes this tracking information as an argument and uses that to
         *
         * @param track     Data taken from tracking a mouse stroke
         */
        public void constructWithTrack(ArrayList<Point2D> track);

    }

}
