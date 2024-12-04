package raven.datetime.component;

import com.formdev.flatlaf.FlatClientProperties;
import raven.datetime.util.Utils;

import javax.swing.*;
import java.awt.*;

public class PanelPopupEditor extends JPanel {

    protected JFormattedTextField editor;
    protected JPopupMenu popupMenu;
    protected LookAndFeel oldThemes = UIManager.getLookAndFeel();


    public PanelPopupEditor() {
    }

    public void showPopup() {
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
            popupMenu.putClientProperty(FlatClientProperties.STYLE, "" +
                    "borderInsets:1,1,1,1");
            popupMenu.add(this);
        }
        if (UIManager.getLookAndFeel() != oldThemes) {
            // component in popup not update UI when change themes
            // so need to update when popup show
            SwingUtilities.updateComponentTreeUI(popupMenu);
            oldThemes = UIManager.getLookAndFeel();
        }
        Point point = Utils.adjustPopupLocation(popupMenu, editor);
        popupMenu.show(editor, point.x, point.y);
    }

    public void closePopup() {
        if (popupMenu != null) {
            popupMenu.setVisible(false);
            repaint();
        }
    }
}
