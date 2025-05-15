package test.renderer;

import com.formdev.flatlaf.util.UIScale;
import raven.datetime.DatePicker;
import raven.datetime.component.date.ButtonDate;
import raven.datetime.component.date.DefaultDateCellRenderer;
import raven.datetime.component.date.SingleDate;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomDateCellRenderer extends DefaultDateCellRenderer {

    private List<DateStatus> list;

    public CustomDateCellRenderer() {
        // init list
        list = new ArrayList<>();
        list.add(new DateStatus(LocalDate.of(2025, 5, 12), LabelDate.MOSTLY_BOOKED));
        list.add(new DateStatus(LocalDate.of(2025, 5, 13), LabelDate.FREE));
        list.add(new DateStatus(LocalDate.of(2025, 5, 16), LabelDate.FULLY_BOOKED));
        list.add(new DateStatus(LocalDate.of(2025, 5, 20), LabelDate.SOME_SLOTS));
        list.add(new DateStatus(LocalDate.of(2025, 5, 23), LabelDate.MOSTLY_BOOKED));
        list.add(new DateStatus(LocalDate.of(2025, 5, 28), LabelDate.MOSTLY_FREE));

        list.add(new DateStatus(LocalDate.of(2025, 6, 26), LabelDate.SOME_SLOTS));
    }

    @Override
    public void paint(Graphics2D g2, DatePicker datePicker, ButtonDate component, SingleDate date, float width, float height) {
        super.paint(g2, datePicker, component, date, width, height);
        LabelDate labelDate = getLabelDate(date.toLocalDate());
        if (labelDate != null) {
            g2.setColor(labelDate.getColor());

            // use rendering hint to control draw strokes line
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setStroke(new BasicStroke(UIScale.scale(getSelectedFocusWidth()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
            Rectangle2D.Float rec = getRectangle(width, height);
            g2.draw(new Arc2D.Float(rec, 90, 360 * labelDate.getValue(), Arc2D.OPEN));
        }
    }

    private LabelDate getLabelDate(LocalDate date) {
        for (DateStatus d : list) {
            if (d.getDate().equals(date)) {
                return d.getLabel();
            }
        }
        return null;
    }

    private class DateStatus {

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LabelDate getLabel() {
            return label;
        }

        public void setLabel(LabelDate label) {
            this.label = label;
        }

        public DateStatus(LocalDate date, LabelDate label) {
            this.date = date;
            this.label = label;
        }

        private LocalDate date;
        private LabelDate label;
    }
}
