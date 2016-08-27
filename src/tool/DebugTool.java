package tool;

import framework.CanvasElement;
import framework.CanvasPanel;
import framework.CursorTool;
import framework.HobbyAnim;
import vector.VectorComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * DebugTool! Doesn't do much but tests out methods of CursorTool and its implementation.
 * TODO Remove from final version.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class DebugTool extends CursorTool {

    /**
     * Basic stroke tracking.
     *
     * @param e     MouseEvent to use
     */
    @Override
    public void startTrack(MouseEvent e) {

        super.startTrack(e);
        System.out.println("startTrack@DebugTool");

    }

    /**
     * Basic stroke tracking.
     *
     * @param e     MouseEvent to use
     */
    @Override
    public void updateTrack(MouseEvent e) {

        super.updateTrack(e);

    }

    /**
     * Create a new command to execute.
     *
     * @return a new TrackCommand
     */
    @Override
    protected TrackCommand createCommand() {

        /**
         * Debug TrackCommand.
         *
         */
        return new TrackCommand() {

            private ArrayList<Point2D> track;
            private CanvasElement ce;
            private long id;

            @Override
            public void constructWithTrack(ArrayList<Point2D> track) {

                this.track = track;
                final ArrayList<Point2D> passTrack = track;
                this.ce = new VectorComponent(0L) {

                    @Override
                    public void paint(Graphics2D g2d) {

                        for(Point2D p : passTrack) g2d.drawOval((int) p.getX() - 2, (int) p.getY() - 2, 4, 4);

                    }

                };

            }

            @Override
            public void undo() {

                System.out.println("undo@DebugTool");
                HobbyAnim.getCurrentFrame().remove(id);

            }

            @Override
            public void execute() {

                System.out.println("execute@DebugTool");
                id = HobbyAnim.getCurrentFrame().put(ce);
                ce.setId(id);

            }

        };

    }

    /**
     *
     */
    @Override
    public void paint(Graphics2D g2d) {

        if(track != null) {

            Point2D p = track.get(0);
            Point2D q = track.get(track.size() - 1);
            g2d.drawLine((int) p.getX(), (int) p.getY(), (int) q.getX(), (int) q.getY());
            for(Point2D r : track) g2d.drawOval((int) r.getX() - 2, (int) r.getY() - 2, 4, 4);

        }

    }
}
