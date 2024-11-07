package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;

public class PanelMonth extends JPanel {

    private final EventMonthChanged monthChanged;
    private final DateSelection dateSelection;
    private final int month;
    private final int year;

    public PanelMonth(EventMonthChanged monthChanged, DateSelection dateSelection, int month, int year) {
        this.monthChanged = monthChanged;
        this.dateSelection = dateSelection;
        this.month = month;
        this.year = year;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        setLayout(new MigLayout("novisualpadding,wrap 3,insets 0,fillx,gap 0,al center center", "fill,sg main", "fill"));
        int count = 12;
        for (int i = 0; i < count; i++) {
            final int month = i;
            ButtonMonthYear button = new ButtonMonthYear(dateSelection, i, false);
            button.setText(DateFormatSymbols.getInstance().getMonths()[i]);
            if (checkSelected(month + 1)) {
                button.setSelected(true);
            }
            button.addActionListener(e -> {
                monthChanged.monthSelected(month);
            });
            add(button);
        }
    }

    protected boolean checkSelected(int month) {
        if (dateSelection.dateSelectionMode == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            return dateSelection.getDate() != null && year == dateSelection.getDate().getYear() && month == dateSelection.getDate().getMonth();
        } else {
            return (dateSelection.getDate() != null && year == dateSelection.getDate().getYear() && month == dateSelection.getDate().getMonth()) ||
                    (dateSelection.getToDate() != null && year == dateSelection.getToDate().getYear() && month == dateSelection.getToDate().getMonth());
        }
    }

    protected void checkSelection() {
        for (int i = 0; i < getComponentCount(); i++) {
            Component com = getComponent(i);
            if (com instanceof ButtonMonthYear) {
                ButtonMonthYear button = (ButtonMonthYear) com;
                button.setSelected(checkSelected(button.getValue() + 1));
            }
        }
    }

    public interface EventMonthChanged {

        void monthSelected(int month);
    }
}
