package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class PanelDate extends JPanel {

    private final DateSelection dateSelection;
    private final int month;
    private final int year;

    public PanelDate(DateSelection dateSelection, int month, int year) {
        this.dateSelection = dateSelection;
        this.month = month;
        this.year = year;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        setLayout(new MigLayout("novisualpadding,wrap 7,insets 3,gap 0,al center center", "fill", "[fill]10[fill][fill]"));
        load();
    }

    protected void load() {
        removeAll();
        createDateHeader();
        final int col = 7;
        final int row = 6;
        final int t = col * row;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONDAY, month);
        calendar.set(Calendar.DATE, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -startDay);
        int rowIndex = 0;
        for (int i = 1; i <= t; i++) {
            SingleDate singleDate = new SingleDate(calendar);
            boolean selectable = dateSelection.getDateSelectionAble() == null || dateSelection.getDateSelectionAble().isDateSelectedAble(singleDate.toLocalDate());
            boolean enable = calendar.get(Calendar.MONDAY) == month && calendar.get(Calendar.YEAR) == year;
            JButton button = createButton(new SingleDate(calendar), enable, rowIndex);
            if (!selectable) {
                button.setEnabled(false);
            }
            add(button);
            calendar.add(Calendar.DATE, 1);
            if (rowIndex == 6) {
                rowIndex = 0;
            } else {
                rowIndex++;
            }
        }
        checkSelection();
    }

    protected void createDateHeader() {
        String weekdays[] = DateFormatSymbols.getInstance().getShortWeekdays();
        for (String week : weekdays) {
            if (!week.isEmpty()) {
                add(createLabel(week));
            }
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten($Label.foreground,30%);" +
                "[dark]foreground:darken($Label.foreground,30%)");
        return label;
    }

    protected JButton createButton(SingleDate date, boolean enable, int rowIndex) {
        ButtonDate button = new ButtonDate(dateSelection, date, enable, rowIndex);
        if (button.isDateSelected()) {
            button.setSelected(true);
        }
        return button;
    }

    protected void checkSelection() {
        for (int i = 0; i < getComponentCount(); i++) {
            Component com = getComponent(i);
            if (com instanceof ButtonDate) {
                ButtonDate buttonDate = (ButtonDate) com;
                if (dateSelection.dateSelectionMode == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
                    buttonDate.setSelected(buttonDate.getDate().same(dateSelection.getDate()));
                } else {
                    buttonDate.setSelected(buttonDate.getDate().same(dateSelection.getDate()) || buttonDate.getDate().same(dateSelection.getToDate()));
                }
            }
        }
    }
}
