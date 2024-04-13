package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Calendar;

public class PanelDateOption extends JPanel {

    private final DatePicker datePicker;
    private boolean disableChange;

    public PanelDateOption(DatePicker datePicker) {
        this.datePicker = datePicker;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        setLayout(new MigLayout("wrap,insets 5,fillx", "[fill]", "[][][][][][][]push[]"));
        add(new JSeparator(SwingConstants.VERTICAL), "dock west");
        buttonGroup = new ButtonGroup();
        add(createButton("Today", 0));
        add(createButton("Yesterday", -1));
        add(createButton("Last 7 Days", -7));
        add(createButton("Last 30 Days", -30));
        add(createButton("This Month", 1, true));
        add(createButton("Last Month", 2, true));
        add(createButton("Last Year", 3, true));

        add(createButton("Custom", -1, true));
    }

    private JToggleButton createButton(String name, int date) {
        return createButton(name, date, false);
    }

    private JToggleButton createButton(String name, int date, boolean useType) {
        JToggleButton button = new JToggleButton(name);
        button.setHorizontalAlignment(SwingConstants.LEADING);

        if (useType) {
            button.addActionListener(e -> {
                disableChange = true;
                if (date == -1) {
                    datePicker.clearSelectedDate();
                } else if (date == 1) {
                    // this month
                    Calendar calendar = Calendar.getInstance();
                    setSelectedDate(calendar);
                } else if (date == 2) {
                    // last month
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, -1);
                    setSelectedDate(calendar);
                } else if (date == 3) {
                    // last year
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, -1);
                    int year = calendar.get(Calendar.YEAR);
                    if (datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
                        datePicker.setSelectedDate(LocalDate.of(year, 1, 1));
                    } else {
                        datePicker.setSelectedDateRange(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
                    }
                }
            });

        } else {
            button.addActionListener(e -> {
                disableChange = true;
                if (date == 0 || date == -1 || datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
                    datePicker.setSelectedDate(calculateDate(date));
                } else {
                    datePicker.setSelectedDateRange(calculateDate(date), LocalDate.now());
                }
            });
        }

        button.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:10;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "margin:4,10,4,10;" +
                "background:null");
        buttonGroup.add(button);
        return button;
    }

    private void setSelectedDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int fromDay = 1;
        int toDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            datePicker.setSelectedDate(LocalDate.of(year, month, fromDay));
        } else {
            datePicker.setSelectedDateRange(LocalDate.of(year, month, fromDay), LocalDate.of(year, month, toDay));
        }
    }

    private LocalDate calculateDate(int date) {
        if (date == 0) {
            return LocalDate.now();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DATE);
            return LocalDate.of(year, month, day);
        }
    }

    public void setSelectedCustom() {
        if (!disableChange) {
            JToggleButton button = (JToggleButton) (getComponent(getComponentCount() - 1));
            button.setSelected(true);
        }
        disableChange = false;
    }

    private ButtonGroup buttonGroup;
}
