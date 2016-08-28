package framework;

import animation.KeyFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CanvasPanel is the window part that can be drawn in.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class CanvasPanel extends JPanel {



    /**
     * Default constructor for CanvasPanel. Initializes some basic settings.
     */
    public CanvasPanel() {

        super();
        setBorder(new LineBorder(Color.BLACK, 1));

        // Add the custom mouse adapter.
        CanvasPanelAdapter cpa = new CanvasPanelAdapter(this);
        addMouseListener(cpa);
        addMouseMotionListener(cpa);

    }

    /**
     * Override the paint method to include painting of other components.
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        KeyFrame onionFrame = HobbyAnim.getFrameAt(HobbyAnim.currentLayerId, HobbyAnim.currentPosition - 1);
        if(onionFrame != null) {

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25F));
            for(CanvasElement ce : onionFrame.values()) ce.paint(g2d);

        }
        g2d.setComposite(AlphaComposite.Src);

        if(HobbyAnim.getCurrentFrame() != null)

            for(CanvasElement ce : HobbyAnim.getCurrentFrame().values()) ce.paint(g2d);


        HobbyAnim.cursor.paint(g2d);

    }

    /**
     * CanvasPanelAdapter is an aptly named adapter for mouse events on the CanvasPanel.
     */
    private class CanvasPanelAdapter extends MouseAdapter {

        /**
         * Parent CanvasPanel.
         */
        private CanvasPanel parent;

        /**
         * Construct with a parent CanvasPanel.
         */
        CanvasPanelAdapter(CanvasPanel parent) {

            super();
            this.parent = parent;

        }

        /**
         * If pressed, do whatever action is specified by the current tool.
         *
         * @param e     MouseEvent to use
         */
        @Override
        public void mousePressed(MouseEvent e) {

            requestFocusInWindow();

            if(SwingUtilities.isLeftMouseButton(e)) {

                HobbyAnim.cursor.startTrack(e);
                parent.repaint();

            }

        }

        /**
         * Continue tracking the stroke.
         *
         * @param e     MouseEvent to use
         */
        @Override
        public void mouseDragged(MouseEvent e) {

            if(SwingUtilities.isLeftMouseButton(e)) {

                HobbyAnim.cursor.updateTrack(e);
                parent.repaint();

            }

        }

        /**
         * Stop tracking and finalize the action, should also make a new UndoCommand.
         *
         * @param e     MouseEvent to use
         */
        @Override
        public void mouseReleased(MouseEvent e) {

            if(SwingUtilities.isLeftMouseButton(e)) {

                HobbyAnim.cursor.endTrack(e);
                parent.repaint();

            }

        }

    }

}
