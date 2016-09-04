package framework;

import icon.GlobalIcon;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        setPreferredSize(new Dimension(200, 1));

        // Add a grid of buttons to switch between tool options.
        JPanel toolGrid = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolGrid.setMinimumSize(new Dimension(150, 1));
        toolGrid.setMaximumSize(new Dimension(150, 1000));

        JButton debugButton = constructToolButton(GlobalIcon.TOOL_BRUSH, HobbyAnim.toolDebugAction);
        JButton inkpenButton = constructToolButton(GlobalIcon.TOOL_INKPEN, HobbyAnim.toolInkpenAction);

        // Foreground color chooser.
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

        // Background color chooser.
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
                HobbyAnim.background = JColorChooser.showDialog(jcc, "Background Color", HobbyAnim.background);
                backgroundColor.repaint();

            }

        });

        // Swap fg/bg colors button.
        JLabel swapLabel = new JLabel(new GlobalIcon().loadIcon(GlobalIcon.SWAP_HORIZONTAL));
        swapLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                Color hold = HobbyAnim.background;
                HobbyAnim.background = HobbyAnim.foreground;
                HobbyAnim.foreground = hold;
                foregroundColor.repaint();
                backgroundColor.repaint();

            }

        });

        // Tool width spinner.
        final JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 1000, 1));
        widthSpinner.setPreferredSize(new Dimension(100, 20));
        widthSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) { HobbyAnim.width = (int) widthSpinner.getValue(); }

        });

        // Construct the tool panel.
        toolGrid.add(debugButton);
        toolGrid.add(inkpenButton);
        add(toolGrid);

        // Construct the colors panel (FG, BG, swapper).
        JPanel colorPanel = new JPanel();
        colorPanel.add(foregroundColor);
        colorPanel.add(swapLabel);
        colorPanel.add(backgroundColor);
        add(colorPanel);

        // Construct the options panel (width).
        JPanel options = new JPanel();
        options.add(new JLabel("Width:"));
        options.add(widthSpinner);
        add(options);

    }

    /**
     * Convenience method that constructs a tool button.
     *
     * @param icon      icon string to load from GlobalIcon
     * @param action    Action to be performed upon clicking
     * @return a new JButton that can be clicked to switch tools
     */
    private JButton constructToolButton(String icon, Action action) {

        final Action action_ = action;

        JButton button = new JButton(new GlobalIcon().loadIcon(icon));
        button.setPreferredSize(new Dimension(32, 32));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) { action_.actionPerformed(e); }

        });

        return button;

    }

}
