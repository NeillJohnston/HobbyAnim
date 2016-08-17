package vector;

import framework.CanvasElement;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Abstract representation for an on-screen vector component.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public abstract class VectorComponent implements CanvasElement {

    /**
     * Unique long ID for this element.
     */
    private long id;

    /**
     * Geometric construction variables.
     */
    protected ArrayList<Point2D> nodes;
    protected Point2D origin;

    /**
     * Default constructor.
     */
    public VectorComponent(long id) {

        this.id = id;
        nodes = new ArrayList<>();

    }

    /**
     * Return this element's unique long ID.
     *
     * @return  id
     */
    public long getID() { return id; }

}
