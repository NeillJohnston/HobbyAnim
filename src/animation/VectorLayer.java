package animation;

/**
 * VectorLayer is an exension of Layer that accepts vector objects.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class VectorLayer extends Layer {

    /**
     * Default constructor.
     */
    public VectorLayer() {

        super();

    };

    /**
     * Insert a new VectorFrame.
     *
     * @param position  frame position (timeline)
     */
    @Override
    public void newFrame(long position) {

        put(position, new VectorFrame());

    }
}
