package raven.datetime.util;

import javax.swing.*;
import java.awt.*;

public class Utils {

    public static Point adjustPopupLocation(JPopupMenu popupMenu, Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        Insets frameInsets = window.getInsets();
        Dimension popupSize = popupMenu.getComponent().getPreferredSize();
        if (window == null) {
            return new Point(0, component.getHeight());
        }
        int frameWidth = window.getWidth() - (frameInsets.left + frameInsets.right);
        int frameHeight = window.getHeight() - (frameInsets.top + frameInsets.bottom);
        Point locationOnFrame = SwingUtilities.convertPoint(component, new Point(0, component.getHeight()), window);
        int bottomSpace = frameHeight - locationOnFrame.y - popupSize.height;
        int x = Math.max(Math.min(locationOnFrame.x, frameInsets.left + frameWidth - popupSize.width), frameInsets.left);
        int y = frameInsets.top + bottomSpace > 0 ? locationOnFrame.y : Math.max(locationOnFrame.y - component.getHeight() - popupSize.height, frameInsets.top);
        return SwingUtilities.convertPoint(window, new Point(x, y), component);
    }
}
