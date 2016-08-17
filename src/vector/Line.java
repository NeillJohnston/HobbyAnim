package vector;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * A simple line with constant width and round ends.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class Line extends VectorComponent {

    /**
     *
     */
    private long id;

    /**
     * Settings of this line.
     */
    private Color color;
    private int brushSize;

    /**
     * Construct a line from a color, brush size, and starting point.
     *
     *
     */
    public Line(long id, Color color, int brushSize, Point2D firstNode) {

        super(id);
        this.color = color;
        this.brushSize = brushSize;
        this.nodes.add(firstNode);

    }

    @Override
    public void paint(Graphics2D g2d) {



    }

}
