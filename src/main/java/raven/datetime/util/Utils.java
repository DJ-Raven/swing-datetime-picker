package raven.datetime.util;

import javax.swing.*;
import java.awt.*;

public class Utils {

    public static Point adjustPopupLocation(JPopupMenu popupMenu, Component component) {
        Frame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
        Insets frameInsets = frame.getInsets();
        Dimension popupSize = popupMenu.getComponent().getPreferredSize();
        if (frame == null) {
            return new Point(0, component.getHeight());
        }
        int frameWidth = frame.getWidth() - (frameInsets.left + frameInsets.right);
        int frameHeight = frame.getHeight() - (frameInsets.top + frameInsets.bottom);
        Point locationOnFrame = SwingUtilities.convertPoint(component, new Point(0, component.getHeight()), frame);
        int bottomSpace = frameHeight - locationOnFrame.y - popupSize.height;
        int x = Math.max(Math.min(locationOnFrame.x, frameInsets.left + frameWidth - popupSize.width), frameInsets.left);
        int y = frameInsets.top + bottomSpace > 0 ? locationOnFrame.y : Math.max(locationOnFrame.y - component.getHeight() - popupSize.height, frameInsets.top);
        return SwingUtilities.convertPoint(frame, new Point(x, y), component);
    }
}
