package raven.datetime.component.date;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.UIScale;
import raven.datetime.DatePicker;
import raven.datetime.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class DefaultDateCellRenderer {

    public void paint(Graphics2D g2, DatePicker datePicker, ButtonDate component, SingleDate date, float width, float height) {

        // paint selection
        float arc = getArc(datePicker);
        boolean isSelected = component.isDateSelected();
        paintDateSelection(g2, datePicker, component, isSelected, width, height, arc);

        //  paint date between selected
        DateSelectionModel dateSelectionModel = datePicker.getDateSelectionModel();
        if (dateSelectionModel.getDateSelectionMode() == DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED && dateSelectionModel.getDate() != null) {
            paintBetweenDateSelection(g2, datePicker, component, date, width, height, arc);
        }
        if (date.same(new SingleDate())) {
            paintCurrentDate(g2, datePicker, component, date, isSelected, width, height, arc);
        }
    }

    /**
     * Paint cell selection or cell focused
     */
    protected void paintDateSelection(Graphics2D g2, DatePicker datePicker, ButtonDate component, boolean isSelected, float width, float height, float arc) {
        Rectangle2D.Float rec = getRectangle(width, height);
        g2.setColor(getSelectionColor(datePicker, component, isSelected));
        g2.fill(FlatUIUtils.createComponentRectangle(rec.x, rec.y, rec.width, rec.height, arc));
    }

    protected void paintBetweenDateSelection(Graphics2D g2, DatePicker datePicker, ButtonDate component, SingleDate date, float width, float height, float arc) {
        g2.setColor(getBetweenDateColor(datePicker, component));
        DateSelectionModel dateSelectionModel = datePicker.getDateSelectionModel();
        Rectangle2D.Float rec = getRectangle(width, height);
        int rowIndex = component.getRowIndex();
        if (date.between(dateSelectionModel.getDate(), getToDate(datePicker))) {
            if (rowIndex == 0) {
                g2.fill(createShape(rec.x, rec.y, width, rec.height, arc, true, true));
            } else if (rowIndex == 6) {
                g2.fill(createShape(rec.x, rec.y, width, rec.height, arc, false, true));
            } else {
                g2.fill(new Rectangle2D.Float(0, rec.y, width, rec.height));
            }
        }
        if (!dateSelectionModel.getDate().same(getToDate(datePicker))) {
            boolean right = dateSelectionModel.getDate().before(getToDate(datePicker));
            if (date.same(dateSelectionModel.getDate())) {
                if ((right && rowIndex != 6) || !right && rowIndex != 0) {
                    g2.fill(createShape(rec.x, rec.y, width, rec.height, arc, right, false));
                }
            }
            if (date.same(getToDate(datePicker))) {
                if ((right && rowIndex != 0) || (!right && rowIndex != 6)) {
                    g2.fill(createShape(rec.x, rec.y, width, rec.height, arc, !right, !component.isHover() && dateSelectionModel.getToDate() == null));
                }
            }
        }
    }

    protected void paintCurrentDate(Graphics2D g2, DatePicker datePicker, ButtonDate component, SingleDate date, boolean isSelected, float width, float height, float arc) {
        Rectangle2D.Float rec = getRectangle(width, height);
        float fw = getSelectedFocusWidth();
        float space = UIScale.scale(isSelected ? fw : 0);
        if (space > 0) {
            rec.x -= space;
            rec.y -= space;
            rec.width += space * 2f;
            rec.height += space * 2f;
        }
        Area area = new Area(FlatUIUtils.createComponentRectangle(rec.x, rec.y, rec.width, rec.height, arc));
        if (isSelected) {
            float s = UIScale.scale(fw + 1f);
            area.subtract(new Area(FlatUIUtils.createComponentRectangle(rec.x + s, rec.x + s, rec.width - s * 2f, rec.height - s * 2f, arc - s)));
        } else {
            float s = UIScale.scale(fw);
            area.subtract(new Area(FlatUIUtils.createComponentRectangle(rec.x + s, rec.y + s, rec.width - s * 2f, rec.height - s * 2f, arc - s)));
        }
        Color accentColor = getAccentColor(datePicker);
        g2.setColor(isSelected ? getBorderColor(component, accentColor) : accentColor);
        g2.fill(area);
    }

    private SingleDate getToDate(DatePicker datePicker) {
        DateSelectionModel dateSelectionModel = datePicker.getDateSelectionModel();
        return dateSelectionModel.getToDate() != null ? dateSelectionModel.getToDate() : dateSelectionModel.getHoverDate();
    }

    protected Shape createShape(float x, float y, float width, float size, float arc, boolean right, boolean add) {
        Area area;
        if (right) {
            area = new Area(new Rectangle2D.Float(width / 2, y, width / 2, size));
            area.subtract(new Area(FlatUIUtils.createComponentRectangle(x, y, size, size, arc)));
        } else {
            area = new Area(new Rectangle2D.Float(0, y, width / 2, size));
        }
        if (add) {
            area.add(new Area(FlatUIUtils.createComponentRectangle(x, y, size, size, arc)));
        } else {
            area.subtract(new Area(FlatUIUtils.createComponentRectangle(x, y, size, size, arc)));
        }
        return area;
    }

    protected Color getBorderColor(ButtonDate component, Color color) {
        return ColorFunctions.mix(color, component.getParent().getBackground(), 0.45f);
    }

    protected Color getBetweenDateColor(DatePicker datePicker, ButtonDate component) {
        Color color = FlatUIUtils.getParentBackground(component);
        if (datePicker.getDateSelectionModel().getToDate() != null) {
            return ColorFunctions.mix(color, getAccentColor(datePicker), 0.9f);
        }
        return FlatLaf.isLafDark() ? ColorFunctions.lighten(color, 0.03f) : ColorFunctions.darken(color, 0.03f);
    }

    /**
     * Get selection color, focused color or isPress and isHover color
     */
    protected Color getSelectionColor(DatePicker datePicker, ButtonDate component, boolean isSelected) {
        // use component parent background as the focused color
        Color color = FlatUIUtils.getParentBackground(component);
        if (isSelected) {
            // use accent color as selection color
            color = getAccentColor(datePicker);
        }
        return Utils.getColor(color, component.isPress(), component.isHover());
    }

    protected Color getAccentColor(DatePicker datePicker) {
        if (datePicker.getColor() != null) {
            return datePicker.getColor();
        }
        return UIManager.getColor("Component.accentColor");
    }

    protected float getCellPadding() {
        return 7f;
    }

    protected float getSelectedFocusWidth() {
        return 2f;
    }

    protected float getArc(DatePicker datePicker) {
        return datePicker.getSelectionArc();
    }

    protected Rectangle2D.Float getRectangle(float width, float height) {
        float size = Math.min(width, height) - UIScale.scale(getCellPadding());
        float x = (width - size) / 2f;
        float y = (height - size) / 2f;
        return new Rectangle2D.Float(x, y, size, size);
    }
}
