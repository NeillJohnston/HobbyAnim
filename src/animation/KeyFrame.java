package animation;

import framework.CanvasElement;

import java.util.HashMap;

/**
 * KeyFrame is an abstract representation of a frame - a collection of things that draw to or modify the canvas.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public abstract class KeyFrame extends HashMap<Long, CanvasElement> {

    /**
     * Where the HashMap is in the index.
     */
    private Long currentID;

    /**
     * Default constructor.
     */
    public KeyFrame() {

        super();
        currentID = 0L;

    }

    /**
     * Put a new value in and get its key, then update.
     */
    public Long put(CanvasElement value) {

        // Keep incrementing the current key value until we find an unused key.
        do {

            currentID++;

        } while(this.containsKey(currentID));

        put(currentID, value);
        return currentID;

    }

}
