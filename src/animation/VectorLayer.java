package animation;

import framework.HobbyAnim;
import framework.OverviewPanel;
import framework.UndoCommand;

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

    /**
     * UndoCommand to create a new VectorLayer.
     */
    public static class NewVectorLayerCommand implements UndoCommand {

        private long id;

        @Override
        public void undo() { HobbyAnim.layers.remove(id); }

        @Override
        public void execute() {

            HobbyAnim.layers.put(HobbyAnim.currentLayerId, new VectorLayer());
            id = HobbyAnim.currentLayerId;
            HobbyAnim.overview.timeline.addLayer(id);
            System.out.printf("Created new VectorLayer[id=%d]\n", id);

        }
    }

}
