package tool;

import framework.CursorTool;
import framework.HobbyAnim;
import vector.VectorComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * InkPenTool has a stroke that thins out the faster you drag your cursor.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class InkPenTool extends CursorTool {

    private static final double DEFAULT_WIDTH = 20;

    /**
     * Create the command to put in the UndoManager stack.
     *
     * @return a new TrackCommand that can create ink pen CanvasElements
     */
    @Override
    protected TrackCommand createCommand() {

        return new TrackCommand() {

            private long id;
            private ArrayList<Point2D> track;
            private VectorComponent stroke;

            private long layerId = HobbyAnim.currentLayerId;
            private long position = HobbyAnim.currentPosition;
            private Color foreground = HobbyAnim.foreground;
            private float width = HobbyAnim.width;

            @Override
            public void constructWithTrack(final ArrayList<Point2D> track) {

                this.track = track;
                final ArrayList<Point2D> track_ = track;
                this.stroke = new VectorComponent() {

                    @Override
                    public void paint(Graphics2D g2d) {

                        if(track_ != null && track.size() >= 1) {

                            Point2D last = track_.get(0);
                            for(Point2D p : track_.subList(1, track_.size() - 1)) {

                                // Get the width of this part of the stroke as a function of its distance from the last point.
                                float calcWidth = width - Math.min(width - 1, (float) p.distance(last) / 4);

                                g2d.setStroke(new BasicStroke(calcWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                                g2d.setColor(foreground);
                                g2d.drawLine((int) last.getX(), (int) last.getY(), (int) p.getX(), (int) p.getY());
                                last = p;

                            }

                        }

                    }

                };

            }

            @Override
            public void undo() {

                HobbyAnim.getFrameAt(layerId, position).remove(id);

            }

            @Override
            public void execute() {

                id = HobbyAnim.getFrameAt(layerId, position).put(stroke);
                stroke.setId(id);

            }

        };

    }

    /**
     * Override paint to draw a simple stroke.
     *
     * @param g2d   Graphics2D object to paint with
     */
    @Override
    public void paint(Graphics2D g2d) {

        if(track != null && track.size() >= 2) {

            Point2D last = track.get(0);
            for(Point2D p : track.subList(1, track.size() - 1)) {

                // Get the width of this part of the stroke as a function of its distance from the last point.
                float calcWidth = HobbyAnim.width - Math.min(HobbyAnim.width - 1, (float) p.distance(last) / 4);

                g2d.setStroke(new BasicStroke(calcWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g2d.setColor(HobbyAnim.foreground);
                g2d.drawLine((int) last.getX(), (int) last.getY(), (int) p.getX(), (int) p.getY());
                last = p;

            }

        }

    }
}
