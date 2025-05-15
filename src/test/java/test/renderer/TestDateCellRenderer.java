package test.renderer;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;
import test.TestFrame;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDateCellRenderer extends TestFrame {

    public TestDateCellRenderer() {
        setLayout(new MigLayout(""));
        DatePicker datePicker = new DatePicker();
        datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.addDateSelectionListener(dateEvent -> {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if (datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
                LocalDate date = datePicker.getSelectedDate();
                if (date != null) {
                    System.out.println("date change " + df.format(datePicker.getSelectedDate()));
                } else {
                    System.out.println("date change to null");
                }
            } else {
                LocalDate dates[] = datePicker.getSelectedDateRange();
                if (dates != null) {
                    System.out.println("date change " + df.format(dates[0]) + " to " + df.format(dates[1]));
                } else {
                    System.out.println("date change to null");
                }
            }
        });

        datePicker.setDefaultDateCellRenderer(new CustomDateCellRenderer());
        datePicker.setSelectedDate(LocalDate.of(2025, 5, 10));
        // date picker minimum size 300px
        add(datePicker, "width 300::,height 330::");
    }


    public static void main(String[] args) {
        // System.setProperty("flatlaf.uiScale", "150%");
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new TestDateCellRenderer().setVisible(true));
    }
}
