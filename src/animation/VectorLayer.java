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
     * Override put method so that the KeyFrame is wrapped as a VectorFrame.
     *
     * @param key       frame position
     * @param value     KeyFrame to add
     * @return whatever super.put(K, V) does
     */
    @Override
    public KeyFrame put(Long key, KeyFrame value) {

        return super.put(key, value);

    }

}
