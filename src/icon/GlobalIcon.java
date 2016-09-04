package icon;

import javax.swing.ImageIcon;
import java.awt.*;

/**
 * GlobalIcon loads icons. That's really all.
 *
 * @author Neill Johnston
 * @version 0.0
 */
public class GlobalIcon {

    /**
     * Icon resource paths.
     */
    public static final String UNDO = "undo.png";
    public static final String REDO = "redo.png";
    public static final String SWAP_HORIZONTAL = "swap_horizontal.png";
    public static final String TOOL_INKPEN = "tool_inkpen.png";
    public static final String TOOL_BRUSH = "tool_brush.png";

    /**
     * Load an icon from the specified path.
     *
     * @param icon  Path to the icon
     * @return a new ImageIcon constructed from the path icon
     */
    public ImageIcon loadIcon(String icon) { return new ImageIcon(getClass().getResource(icon)); }

    /**
     * Load an icon specifically for menus (must be scaled to size).
     */
    public ImageIcon loadMenuIcon(String icon) {

        return new ImageIcon(new ImageIcon(
                getClass().getResource(icon))
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH
                ));

    }

}
