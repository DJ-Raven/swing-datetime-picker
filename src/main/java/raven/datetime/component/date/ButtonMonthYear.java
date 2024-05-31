package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMonthYear extends JButton {

    public int getValue() {
        return value;
    }

    private final DateSelection dateSelection;
    private final int value;
    private final boolean isYear;
    private boolean press;
    private boolean hover;

    public ButtonMonthYear(DateSelection dateSelection, int value, boolean isYear) {
        this.dateSelection = dateSelection;
        this.value = value;
        this.isYear = isYear;
        init();
    }

    private void init() {
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    press = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    press = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
            }
        });
        putClientProperty(FlatClientProperties.STYLE, "" +
                "margin:6,6,6,6;" +
                "selectedForeground:contrast($Component.accentColor,$Button.background,#fff);");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        FlatUIUtils.setRenderingHints(g2);
        int border = UIScale.scale(6);
        int arc = UIScale.scale(10);
        int width = getWidth() - border;
        int height = getHeight() - border;
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;
        g2.setColor(getColor());
        FlatUIUtils.paintComponentBackground(g2, x, y, width, height, 0, arc);
        g2.dispose();
        super.paintComponent(g);
    }

    protected Color getColor() {
        Color color = isSelected() ? getAccentColor() : FlatUIUtils.getParentBackground(this);
        if (press) {
            return FlatLaf.isLafDark() ? ColorFunctions.lighten(color, 0.1f) : ColorFunctions.darken(color, 0.1f);
        } else if (hover) {
            return FlatLaf.isLafDark() ? ColorFunctions.lighten(color, 0.03f) : ColorFunctions.darken(color, 0.03f);
        }
        return color;
    }

    protected Color getAccentColor() {
        if (dateSelection.datePicker.getColor() != null) {
            return dateSelection.datePicker.getColor();
        }
        return UIManager.getColor("Component.accentColor");
    }
}
