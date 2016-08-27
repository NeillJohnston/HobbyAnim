package framework;

import java.awt.*;

/**
 * A paintable and editable element of the canvas.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public interface CanvasElement {

    /**
     * Paint this element using the specified Graphics2D object.
     *
     * @param g2d   Graphics2D object to paint with
     */
    public void paint(Graphics2D g2d);

    /**
     * Return this element's unique long ID.
     *
     * @return id
     */
    public long getId();

    /**
     * Set this element's unique long ID.
     *
     * @param id    The Id.
     */
    public void setId(long id);

}
