package framework;

import animation.KeyFrame;
import animation.Layer;
import animation.VectorLayer;
import tool.DebugTool;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * HobbyAnim is the main class that the entire program can refer to. Will contain methods for managing undo/execute as well
 * as a host of constant variables. It extends JFrame and is the main window of the program.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class HobbyAnim extends JFrame {

    /**
     * Window defaults.
     */
    private final static String WINDOW_NAME = "HobbyAnim";
    private final static int WINDOW_WIDTH = 1280;
    private final static int WINDOW_HEIGHT = 720;

    /**
     * Swing components and other important static objects that comprise the window.
     */
    public static HobbyAnim main;
    public static CanvasPanel canvas;
    public static CursorTool cursor;
    public static UndoManager undoManager;

    /**
     * Layers.
     */
    public static HashMap<Long, Layer> layers;
    public static long currentLayerId;
    public static long currentFrame;

    /**
     * Default constructor for HobbyAnim, initializes all the typical settings.
     */
    public HobbyAnim() {

        // Some default window settings.
        setTitle(WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add the other framework parts.
        canvas = new CanvasPanel();
        add(canvas, BorderLayout.CENTER);
        cursor = new DebugTool();
        undoManager = new UndoManager();

        currentLayerId = 0L;
        currentFrame = 0;
        layers = new HashMap<>();
        layers.put(currentLayerId, new VectorLayer());
        getCurrentLayer().put(currentFrame, new KeyFrame() {});

        // Create and add the menu bar.
        JMenuBar menuBar = new JMenuBar();

            JMenu editMenu = new JMenu("Edit");

            final JMenuItem undoEditMenu = new JMenuItem("Undo (Ctrl+Z)");
            undoEditMenu.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) { undoManager.undo(); }

            });
            editMenu.add(undoEditMenu);

            JMenuItem redoEditMenu = new JMenuItem("Redo (Ctrl+Y)");
            redoEditMenu.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) { undoManager.redo(); }

            });
            editMenu.add(redoEditMenu);

        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        // Pack and display.
        pack();
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setVisible(true);

    }

    /**
     * Instantiate a new HobbyAnim object to begin the program.
     *
     * @param args  command-line arguments
     */
    public static void main(String[] args) { main = new HobbyAnim(); }

    /**
     * Get the current layer.
     *
     * @return current layer
     */
    public static Layer getCurrentLayer() {

        return layers.get(currentLayerId);

    }

    /**
     * Get the current frame.
     *
     * @return current frame.
     */
    public static KeyFrame getCurrentFrame() {

        return getCurrentLayer().get(currentFrame);

    }

}
