package framework;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ToolOptionsPanel sits on the left side of the work area and provides convenient tool-switching and settings.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class ToolOptionsPanel extends JPanel {

    /**
     * Default constructor.
     */
    public ToolOptionsPanel() {

        setPreferredSize(new Dimension(200, 200));
        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Add color choosers.
        final JComponent foregroundColor = new JComponent() {

            private final int WIDTH = 40;
            private final int HEIGHT = 40;

            @Override
            public void paint(Graphics g) {

                g.setColor(HobbyAnim.foreground);
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, WIDTH, HEIGHT);

            }

        };
        foregroundColor.setPreferredSize(new Dimension(40, 40));
        foregroundColor.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                JColorChooser jcc = new JColorChooser();
                HobbyAnim.foreground = jcc.showDialog(jcc, "Foreground Color", HobbyAnim.foreground);
                foregroundColor.repaint();

            }

        });

        final JComponent backgroundColor = new JComponent() {

            private final int WIDTH = 40;
            private final int HEIGHT = 40;

            @Override
            public void paint(Graphics g) {

                g.setColor(HobbyAnim.background);
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, WIDTH, HEIGHT);

            }

        };
        backgroundColor.setPreferredSize(new Dimension(40, 40));
        backgroundColor.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                JColorChooser jcc = new JColorChooser();
                HobbyAnim.background = jcc.showDialog(jcc, "Background Color", HobbyAnim.background);
                backgroundColor.repaint();

            }

        });

        // Add the tool width chooser.
        final JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 1000, 1));
        widthSpinner.setPreferredSize(new Dimension(100, 20));
        widthSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) { HobbyAnim.width = (int) widthSpinner.getValue(); }

        });

        JPanel colorPanel = new JPanel();
        colorPanel.add(foregroundColor);
        colorPanel.add(backgroundColor);
        add(colorPanel);

        JPanel options = new JPanel();
        options.add(new JLabel("Width"));
        options.add(widthSpinner);
        add(options);

    }

}
