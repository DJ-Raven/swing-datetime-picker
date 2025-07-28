package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class PanelDate extends JPanel {

    private final DatePicker datePicker;
    private final int month;
    private final int year;

    public PanelDate(DatePicker datePicker, int month, int year) {
        this.datePicker = datePicker;
        this.month = month;
        this.year = year;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        setLayout(new MigLayout(
                "novisualpadding,wrap 7,insets 3,gap 0,al center center",
                "[fill]",
                "[fill]10[fill][fill]"));
        load();
    }

    public void load() {
        removeAll();
        createDateHeader();
        final int col = 7;
        final int row = 6;
        final int t = col * row;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (datePicker.isStartWeekOnMonday() && dayOfWeek == Calendar.SUNDAY) {
            dayOfWeek = 8;
        }
        int startDay = dayOfWeek - (datePicker.isStartWeekOnMonday() ? 2 : 1);
        calendar.add(Calendar.DATE, -startDay);
        int rowIndex = 0;
        for (int i = 1; i <= t; i++) {
            SingleDate singleDate = new SingleDate(calendar);
            boolean selectable = datePicker.getDateSelectionModel().getDateSelectionAble() == null || datePicker.getDateSelectionModel().getDateSelectionAble().isDateSelectedAble(singleDate.toLocalDate());
            boolean enable = calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.YEAR) == year;
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
        String[] weekdays = DatePicker.getDefaultWeekdays();
        // swap monday to the start day of week
        if (datePicker.isStartWeekOnMonday()) {
            String sunday = weekdays[1];
            for (int i = 2; i < weekdays.length; i++) {
                weekdays[i - 1] = weekdays[i];
            }
            weekdays[weekdays.length - 1] = sunday;
        }
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
        ButtonDate button = new ButtonDate(datePicker, date, enable, rowIndex);
        if (button.isDateSelected()) {
            button.setSelected(true);
        }
        return button;
    }

    public void checkSelection() {
        for (int i = 0; i < getComponentCount(); i++) {
            Component com = getComponent(i);
            if (com instanceof ButtonDate) {
                ButtonDate buttonDate = (ButtonDate) com;
                if (datePicker.getDateSelectionModel().getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
                    buttonDate.setSelected(buttonDate.getDate().same(datePicker.getDateSelectionModel().getDate()));
                } else {
                    buttonDate.setSelected(buttonDate.getDate().same(datePicker.getDateSelectionModel().getDate()) || buttonDate.getDate().same(datePicker.getDateSelectionModel().getToDate()));
                }
            }
        }
    }
}
