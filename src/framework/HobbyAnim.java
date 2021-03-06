package framework;

import animation.KeyFrame;
import animation.Layer;
import animation.VectorLayer;
import icon.GlobalIcon;
import tool.DebugTool;
import tool.InkPenTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * HobbyAnim is the main class that the entire program can refer to. Will contain methods for managing undo/execute as well
 * as a host of constant variables. It extends JFrame and is the main window of the program.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class HobbyAnim extends JFrame {

    /** Window defaults. */
    private final static String WINDOW_NAME = "HobbyAnim";
    private final static int WINDOW_WIDTH = 1280;
    private final static int WINDOW_HEIGHT = 720;

    /** Swing components and other important static objects that comprise the window. */
    public static HobbyAnim main;
    public static CanvasPanel canvas;
    public static ToolOptionsPanel toolOptions;
    public static OverviewPanel overview;
    public static CursorTool cursor;
    public static UndoManager undoManager;

    /** Layer variables. */
    public static HashMap<Long, Layer> layers;
    public static ArrayList<Long> layerOrder;
    public static long currentLayerId;
    public static long currentPosition;

    /** Tool options. */
    public static Color foreground;
    public static Color background;
    public static float width;

    /**
     * Global actions.
     *
     * undoAction: Undo the last UndoCommand.
     * redoAction: Redo the next UndoCommand.
     * newFrameAction: Create a new frame at the current position. If there is already a frame at the current position,
     * move the position one up and repeat.
     * nextFrameAction: Increase the position by 1, if possible.
     * prevFrameAction: Decrease the position by 1, if possible.
     * toolDebugAction: Testing, switches to debug tool.
     * toolInkpenAction: Switches to inkpen tool.
     */
    public static AbstractAction undoAction;
    public static AbstractAction redoAction;
    public static AbstractAction newFrameAction;
    public static AbstractAction nextFrameAction;
    public static AbstractAction prevFrameAction;
    public static AbstractAction toolDebugAction;
    public static AbstractAction toolInkpenAction;

    /**
     * Action string constants (should be one per action above).
     */
    public static final String UNDO_ACTION = "undoAction";
    public static final String REDO_ACTION = "redoAction";
    public static final String NEW_FRAME_ACTION = "newFrameAction";
    public static final String NEXT_FRAME_ACTION = "nextFrameAction";
    public static final String PREV_FRAME_ACTION = "prevFrameAction";
    public static final String TOOL_INKPEN_ACTION = "toolInkpenAction";

    /**
     * Default constructor for HobbyAnim, initializes all the typical settings.
     */
    public HobbyAnim() {

        // Some default window settings.
        setTitle(WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Default tool options.
        foreground = Color.BLACK;
        background = Color.WHITE;
        width = 20;

        // Load list of actions.
        undoAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) { undoManager.undo(); }

        };
        redoAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) { undoManager.redo(); }

        };
        newFrameAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                while(getCurrentLayer().get(currentPosition) != null) { currentPosition++; }
                UndoCommand uc = new UndoCommand() {

                    long position = HobbyAnim.currentPosition;
                    long layerId = HobbyAnim.currentLayerId;

                    @Override
                    public void undo() { HobbyAnim.layers.get(layerId).remove(position); }

                    @Override
                    public void execute() { HobbyAnim.layers.get(layerId).newFrame(position); }

                };
                uc.execute();
                undoManager.add(uc);
                canvas.repaint();
                overview.revalidate();
                overview.repaint();

            }

        };
        nextFrameAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                currentPosition++;
                canvas.repaint();
                overview.revalidate();
                overview.repaint();

            }

        };
        prevFrameAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(currentPosition > 0) currentPosition--;
                canvas.repaint();
                overview.revalidate();
                overview.repaint();

            }

        };
        toolDebugAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) { HobbyAnim.cursor = new DebugTool(); }

        };
        toolInkpenAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) { HobbyAnim.cursor = new InkPenTool(); }

        };

        InputMap im = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), UNDO_ACTION);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), REDO_ACTION);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), NEW_FRAME_ACTION);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), NEXT_FRAME_ACTION);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), PREV_FRAME_ACTION);

        ActionMap am = getRootPane().getActionMap();
        am.put(UNDO_ACTION, undoAction);
        am.put(REDO_ACTION, redoAction);
        am.put(NEW_FRAME_ACTION, newFrameAction);
        am.put(NEXT_FRAME_ACTION, nextFrameAction);
        am.put(PREV_FRAME_ACTION, prevFrameAction);

        // Create and add the other framework parts.
        canvas = new CanvasPanel();
        add(canvas, BorderLayout.CENTER);
        toolOptions = new ToolOptionsPanel();
        add(toolOptions, BorderLayout.WEST);
        overview = new OverviewPanel();
        add(overview, BorderLayout.SOUTH);
        cursor = new InkPenTool();
        undoManager = new UndoManager();

        // Default layer variables.
        currentLayerId = 0L;
        currentPosition = 0L;
        layers = new HashMap<Long, Layer>() {

            /**
             * Override put to make sure layerOrder is updated when layers is and update the current layer ID.
             * // JANKY The currentLayerId is updated to whatever layer was just created, allowing for 1. auto-switching
             * to the newly created layer and 2. retrieving the layer ID for use in commands. Two-birds-one-stone, but
             * damn is that stone ghetto.
             *
             * @param key
             * @param value
             * @return
             */
            @Override
            public Layer put(Long key, Layer value) {

                while(layers.containsKey(key)) key++;
                layerOrder.add(key);
                currentLayerId = key;
                return super.put(key, value);

            }

            /**
             * Override remove to make sure layerOrder is updated when layers is.
             *
             * @param key
             * @return
             */
            @Override
            public Layer remove(Object key) {

                layerOrder.remove(key);
                return super.remove(key);

            }
        };
        layerOrder = new ArrayList<>();
        new VectorLayer.NewVectorLayerCommand().execute();
        getCurrentLayer().newFrame(currentPosition);

        // Create and add the menu bar.
        JMenuBar menuBar = new JMenuBar();

            JMenu editMenu = new JMenu("Edit");

                JMenuItem undoEditMenu = new JMenuItem("Undo", new GlobalIcon().loadMenuIcon(GlobalIcon.UNDO));
                undoEditMenu.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) { undoAction.actionPerformed(e); }

                });
                editMenu.add(undoEditMenu);

                JMenuItem redoEditMenu = new JMenuItem("Redo", new GlobalIcon().loadMenuIcon(GlobalIcon.REDO));
                redoEditMenu.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) { redoAction.actionPerformed(e); }

                });
                editMenu.add(redoEditMenu);

            JMenu frameMenu = new JMenu("Frame");

                JMenuItem newFrameMenu = new JMenuItem("New frame");
                newFrameMenu.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) { newFrameAction.actionPerformed(e); }

                });
                frameMenu.add(newFrameMenu);

                JMenuItem nextFrameMenu = new JMenuItem("Next frame");
                nextFrameMenu.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) { nextFrameAction.actionPerformed(e); }

                });
                frameMenu.add(nextFrameMenu);

                JMenuItem prevFrameMenu = new JMenuItem("Previous frame");
                prevFrameMenu.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) { prevFrameAction.actionPerformed(e); }

                });
                frameMenu.add(prevFrameMenu);

        menuBar.add(editMenu);
        menuBar.add(frameMenu);
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
    public static void main(String[] args) {

        // First set the system look and feel.
        try { UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); }
        catch(Exception e) { System.err.println("LAZY HANDLING"); e.printStackTrace(); }

        // Construct main and start the program. :D
        main = new HobbyAnim();

    }

    /**
     * Get the current layer.
     *
     * @return current layer
     */
    public static Layer getCurrentLayer() { return layers.get(currentLayerId); }

    /**
     * Get the current frame.
     *
     * @return current frame.
     */
    public static KeyFrame getCurrentFrame() { return getCurrentLayer().get(currentPosition); }

    /**
     * Get the frame at the specified layer ID and position.
     * Useful mainly for commands that tie their action to specific frames (and can't just use the current one).
     *
     * @param layer     layer ID
     * @param position  position (timeline)
     */
    public static KeyFrame getFrameAt(long layer, long position) { return layers.get(layer).get(position); }

}
