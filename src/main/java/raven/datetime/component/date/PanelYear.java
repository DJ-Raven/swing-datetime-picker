package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PanelYear extends JPanel {

    public static final int YEAR_CELL = 28;
    private final DatePicker datePicker;
    private final int year;
    private int selectedYear = -1;

    public PanelYear(DatePicker datePicker, int year) {
        this.datePicker = datePicker;
        this.year = year;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        setLayout(new MigLayout(
                "novisualpadding,wrap 4,insets 0,fillx,gap 0,al center center",
                "fill,sg main",
                "fill"));

        final int count = YEAR_CELL;
        for (int i = 0; i < count; i++) {
            final int y = getStartYear(year) + i;
            ButtonMonthYear button = new ButtonMonthYear(datePicker, y);
            button.setText(y + "");
            if (checkSelected(y)) {
                button.setSelected(true);
            }
            button.addActionListener(e -> {
                this.selectedYear = y;
                fireYearChanged(new ChangeEvent(this));
            });
            add(button);
        }
        checkSelection();
    }

    private int getStartYear(int year) {
        int initYear = 1900;
        int currentYear = year;
        int yearsPerPage = YEAR_CELL;
        int yearsPassed = currentYear - initYear;
        int pages = yearsPassed / yearsPerPage;
        int startingYearOnPage = initYear + (pages * yearsPerPage);
        return startingYearOnPage;
    }

    protected boolean checkSelected(int year) {
        DateSelectionModel dateSelectionModel = datePicker.getDateSelectionModel();
        if (dateSelectionModel.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            return dateSelectionModel.getDate() != null && year == dateSelectionModel.getDate().getYear();
        } else {
            return (dateSelectionModel.getDate() != null && year == dateSelectionModel.getDate().getYear()) ||
                    (dateSelectionModel.getToDate() != null && year == dateSelectionModel.getToDate().getYear());
        }
    }

    public void checkSelection() {
        for (int i = 0; i < getComponentCount(); i++) {
            Component com = getComponent(i);
            if (com instanceof ButtonMonthYear) {
                ButtonMonthYear button = (ButtonMonthYear) com;
                button.setSelected(checkSelected(button.getValue()));
            }
        }
    }

    public int getYear() {
        return year;
    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    public void fireYearChanged(ChangeEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                ((ChangeListener) listeners[i + 1]).stateChanged(event);
            }
        }
    }

    public int getSelectedYear() {
        return selectedYear;
    }
}
