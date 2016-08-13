package framework;

import javax.swing.*;
import java.awt.*;

/**
 * HobbyAnim is the main class that the entire program can refer to. Will contain methods for managing undo/redo as well
 * as a host of constant variables. It extends JFrame and is the main window of the program.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class HobbyAnim extends JFrame {

    private static String WINDOW_NAME = "HobbyAnim";
    public static HobbyAnim main;

    /**
     * Instantiate a new HobbyAnim object to begin the program.
     *
     * @param args  command-line arguments
     */
    public static void main(String[] args) {

        main = new HobbyAnim();

    }

    public HobbyAnim() {

        setTitle(WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pack();
        setSize(new Dimension(1280, 720));
        setVisible(true);

    }

}
