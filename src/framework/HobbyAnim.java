package framework;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

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
        cursor = CursorTool.DEBUG;
        undoManager = new UndoManager();

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

}
