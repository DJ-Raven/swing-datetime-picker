package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.UIScale;
import raven.datetime.DatePicker;
import raven.datetime.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ButtonDate extends JButton {

    private final DatePicker datePicker;
    private final SingleDate date;
    private boolean press;
    private boolean hover;
    private int rowIndex;

    public ButtonDate(DatePicker datePicker, SingleDate date, boolean enable, int rowIndex) {
        this.datePicker = datePicker;
        this.date = date;
        this.rowIndex = rowIndex;
        setText(date.getDay() + "");
        init(enable);
    }

    private void init(boolean enable) {
        setContentAreaFilled(false);
        addActionListener(e -> {
            datePicker.getDateSelection().selectDate(date);
            hover = false;
            PanelDate panelDate = (PanelDate) getParent();
            panelDate.checkSelection();
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isEnabled() && datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
                    if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                        datePicker.closePopup();
                    }
                }
            }

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
                if (datePicker.getDateSelection().getDateSelectionMode() == DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED && datePicker.getDateSelection().getDate() != null) {
                    datePicker.getDateSelection().setHoverDate(date);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
            }
        });
        if (enable) {
            putClientProperty(FlatClientProperties.STYLE, "" +
                    "margin:7,7,7,7;" +
                    "focusWidth:2;" +
                    "selectedForeground:contrast($Component.accentColor,$Button.background,#fff)");
        } else {
            putClientProperty(FlatClientProperties.STYLE, "" +
                    "margin:7,7,7,7;" +
                    "focusWidth:2;" +
                    "selectedForeground:contrast($Component.accentColor,$Button.background,#fff);" +
                    "foreground:$Button.disabledText");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        FlatUIUtils.setRenderingHints(g2);
        double width = getWidth();
        double height = getHeight();
        double size = Math.min(width, height) - UIScale.scale(7);
        double x = (width - size) / 2;
        double y = (height - size) / 2;

        g2.setColor(getColor());
        g2.fill(new Ellipse2D.Double(x, y, size, size));
        DateSelectionModel dateSelectionModel = datePicker.getDateSelection();

        //  paint date between selected
        if (dateSelectionModel.getDateSelectionMode() == DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED && dateSelectionModel.getDate() != null) {
            g2.setColor(getBetweenDateColor());
            if (date.between(dateSelectionModel.getDate(), getToDate())) {
                if (rowIndex == 0) {
                    g2.fill(getShape(x, y, width, size, true, true));
                } else if (rowIndex == 6) {
                    g2.fill(getShape(x, y, width, size, false, true));
                } else {
                    g2.fill(new Rectangle2D.Double(0, y, width, size));
                }
            }
            if (!dateSelectionModel.getDate().same(getToDate())) {
                boolean right = dateSelectionModel.getDate().before(getToDate());
                if (date.same(dateSelectionModel.getDate())) {
                    if ((right && rowIndex != 6) || !right && rowIndex != 0) {
                        g2.fill(getShape(x, y, width, size, right, false));
                    }
                }
                if (date.same(getToDate())) {
                    if ((right && rowIndex != 0) || (!right && rowIndex != 6)) {
                        g2.fill(getShape(x, y, width, size, !right, !hover && dateSelectionModel.getToDate() == null));
                    }
                }
            }
        }
        if (date.same(new SingleDate())) {
            boolean isSelected = isDateSelected();
            double space = Math.min(width, height) - UIScale.scale(isSelected ? 2 : 7);
            double xx = (width - space) / 2;
            double yy = (height - space) / 2;
            Area area = new Area(new Ellipse2D.Double(xx, yy, space, space));
            if (isSelected) {
                float s = UIScale.scale(1);
                area.subtract(new Area(new Ellipse2D.Double(x + s, y + s, size - s * 2, size - s * 2)));
            } else {
                float s = UIScale.scale(2);
                area.subtract(new Area(new Ellipse2D.Double(x + s, y + s, size - s * 2, size - s * 2)));
            }
            Color accentColor = getAccentColor();
            g2.setColor(isSelected ? getBorderColor(accentColor) : accentColor);
            g2.fill(area);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    private Shape getShape(double x, double y, double width, double size, boolean right, boolean add) {
        Area area;
        if (right) {
            area = new Area(new Rectangle2D.Double(width / 2, y, width / 2, size));
            area.subtract(new Area(new Ellipse2D.Double(x, y, size, size)));
        } else {
            area = new Area(new Rectangle2D.Double(0, y, width / 2, size));
        }
        if (add) {
            area.add(new Area(new Ellipse2D.Double(x, y, size, size)));
        } else {
            area.subtract(new Area(new Ellipse2D.Double(x, y, size, size)));
        }
        return area;
    }

    private SingleDate getToDate() {
        DateSelectionModel dateSelectionModel = datePicker.getDateSelection();
        return dateSelectionModel.getToDate() != null ? dateSelectionModel.getToDate() : dateSelectionModel.getHoverDate();
    }

    protected boolean isDateSelected() {
        DateSelectionModel dateSelectionModel = datePicker.getDateSelection();
        if (dateSelectionModel.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            return date.same(dateSelectionModel.getDate());
        } else {
            return date.same(dateSelectionModel.getDate()) || date.same(dateSelectionModel.getToDate());
        }
    }

    protected Color getBorderColor(Color color) {
        return ColorFunctions.mix(color, getParent().getBackground(), 0.45f);
    }

    protected Color getBetweenDateColor() {
        Color color = FlatUIUtils.getParentBackground(this);
        if (datePicker.getDateSelection().getToDate() != null) {
            return ColorFunctions.mix(color, getAccentColor(), 0.9f);
        }
        return FlatLaf.isLafDark() ? ColorFunctions.lighten(color, 0.03f) : ColorFunctions.darken(color, 0.03f);
    }

    protected Color getColor() {
        Color color = FlatUIUtils.getParentBackground(this);
        if (isDateSelected()) {
            color = getAccentColor();
        }
        return Utils.getColor(color, press, hover);
    }

    protected Color getAccentColor() {
        if (datePicker.getColor() != null) {
            return datePicker.getColor();
        }
        return UIManager.getColor("Component.accentColor");
    }

    protected SingleDate getDate() {
        return date;
    }
}
