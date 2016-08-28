package animation;

import framework.CanvasElement;

import java.util.HashMap;

/**
 * Representation of a layer - a collection of keyframes.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public abstract class Layer extends HashMap<Long, KeyFrame> {

    /**
     * Default constructor.
     */
    public Layer() {

        super();

    }

    /**
     * Create a new frame.
     *
     * @param position  frame position (timeline)
     */
    public void newFrame(long position) {

        put(position, new KeyFrame() {});

    }

}
