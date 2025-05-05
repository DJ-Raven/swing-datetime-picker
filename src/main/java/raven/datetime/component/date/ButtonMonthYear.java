package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import raven.datetime.DatePicker;
import raven.datetime.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMonthYear extends JButton {

    private final DatePicker datePicker;
    private final int value;
    private boolean press;
    private boolean hover;

    public ButtonMonthYear(DatePicker datePicker, int value) {
        this.datePicker = datePicker;
        this.value = value;
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

    public int getValue() {
        return value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        FlatUIUtils.setRenderingHints(g2);
        int border = UIScale.scale(6);
        float arc = UIScale.scale(datePicker.getSelectionArc());
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
        return Utils.getColor(color, press, hover);
    }

    protected Color getAccentColor() {
        if (datePicker.getColor() != null) {
            return datePicker.getColor();
        }
        return UIManager.getColor("Component.accentColor");
    }
}
