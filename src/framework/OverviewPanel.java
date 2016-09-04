package framework;

import animation.KeyFrame;
import animation.Layer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.Map;

/**
 * OverviewPanel displays all the existing layers, their frames, a timeView, and options to edit/manage layers.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class OverviewPanel extends JPanel {

    /**
     * Parts of this panel.
     */
    public TimelinePanel timeline;

    /**
     * Default constructor.
     */
    public OverviewPanel() {

        setPreferredSize(new Dimension(1, 200));
        setLayout(new BorderLayout(0, 0));

        timeline = new TimelinePanel();
        JScrollPane timelineWrapper = new JScrollPane(timeline);
        timelineWrapper.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        timelineWrapper.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(timelineWrapper, BorderLayout.CENTER);

    }

    /**
     * TimelinePanel is the panel inside of the OverviewPanel that shows the timeView, layers, and frames.
     */
    public class TimelinePanel extends JLayeredPane {

        /** Constants. */
        private final Color SELECTOR_COLOR = new Color(0, 127, 255, (int) (.25 * 255));
        private final Color LAYER_COLOR = new Color(127, 0, 0, (int) (.5 * 255));
        private final Color LAYER_COLOR_ACTIVE = new Color(191, 0, 0, (int) (.5 * 255));
        private final Color LAYER_OUTLINE_COLOR = new Color(63, 0, 0);
        private final Color FRAME_COLOR = new Color(255, 191, 127);
        private final Color FRAME_OUTLINE_COLOR = new Color(127, 63, 0);
        private final int FRAME_WIDTH = 20;
        private final int LAYER_HEIGHT = 30;

        /** Other components. */
        private JComponent selector;

        /**
         * Default constructor.
         */
        public TimelinePanel() {

            super();

            // Panel settings.
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

            // Create and add the selector component.
            selector = new JComponent() {

                @Override
                protected void paintComponent(Graphics g) {

                    super.paintComponent(g);
                    setPreferredSize(new Dimension(TimelinePanel.this.getWidth(), TimelinePanel.this.getHeight()));
                    setLocation(0, 0);
                    g.setColor(SELECTOR_COLOR);
                    g.fillRect((int) HobbyAnim.currentPosition  * FRAME_WIDTH, 0, FRAME_WIDTH, this.getHeight());

                }

            };
            add(selector, new Integer(1));

            // Listen for mouse clicks.
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    HobbyAnim.currentPosition = e.getX() / FRAME_WIDTH;
                    try { HobbyAnim.currentLayerId = HobbyAnim.layerOrder.get(e.getY() / LAYER_HEIGHT); }
                    catch(IndexOutOfBoundsException err) { HobbyAnim.currentLayerId = HobbyAnim.layerOrder.get(HobbyAnim.layerOrder.size() - 1); }
                    revalidate();
                    repaint();
                    HobbyAnim.canvas.repaint();

                }

            });

        }

        /**
         * Add a new layer to the GUI.
         */
        public void addLayer(long id) {

            add(getLayerComponentFromLayer(id));
            revalidate();
            repaint();

        }

        /**
         * Create a component to track a specified layer.
         *
         * @return a JComponent that represents a layer, to be used in the timeline.
         */
        public JComponent getLayerComponentFromLayer(final long id_) {

            final Layer l = HobbyAnim.layers.get(id_);

            return new JComponent() {

                private long id = id_;

                @Override
                protected void paintComponent(Graphics g) {

                    super.paintComponent(g);
                    setPreferredSize(new Dimension(TimelinePanel.this.getWidth(), LAYER_HEIGHT));
                    setLocation(0, HobbyAnim.layerOrder.indexOf(id) * LAYER_HEIGHT);
                    if(HobbyAnim.currentLayerId == id) g.setColor(LAYER_COLOR_ACTIVE);
                    else g.setColor(LAYER_COLOR);
                    g.fillRect(0, 0, getWidth(), LAYER_HEIGHT);
                    g.setColor(LAYER_OUTLINE_COLOR);
                    g.drawRect(0, 0, getWidth(), LAYER_HEIGHT);
                    for(Map.Entry<Long, KeyFrame> e : HobbyAnim.layers.get(id).entrySet()) {

                        g.setColor(FRAME_COLOR);
                        g.fillRect((int) (e.getKey() * FRAME_WIDTH + 3), 3, FRAME_WIDTH - 6, LAYER_HEIGHT - 6);
                        g.setColor(FRAME_OUTLINE_COLOR);
                        g.drawRect((int) (e.getKey() * FRAME_WIDTH + 3), 3, FRAME_WIDTH - 6, LAYER_HEIGHT - 6);

                    }

                }

                @Override
                public void validate() {

                    super.validate();
                    setLocation(0, HobbyAnim.layerOrder.indexOf(id) * LAYER_HEIGHT);

                }

            };

        }

        /**
         * Override paintComponent to draw lines across the background.
         *
         * @param g     Graphics object to use
         */
        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.setColor(Color.DARK_GRAY);
            for(int x = 0; x < this.getWidth(); x += FRAME_WIDTH) g.drawLine(x, 0, x, this.getHeight());

        }

        /**
         * Override validate to resize the panel when changing the current position.
         */
        @Override
        public void validate() {

            super.validate();
            setPreferredSize(new Dimension(
                    Math.max(((int) HobbyAnim.currentPosition + 1) * FRAME_WIDTH, OverviewPanel.this.getWidth()),
                    getHeight()));
            if(selector != null) selector.revalidate();

        }

    }

}
