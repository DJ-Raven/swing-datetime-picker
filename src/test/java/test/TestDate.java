package test;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TestDate extends JFrame {

    public TestDate() {
        String[] customMonths = {
                "Custom Jan", "Custom Feb", "Custom Mar", "Custom Apr", "Custom May", "Custom Jun",
                "Custom Jul", "Custom Aug", "Custom Sep", "Custom Oct", "Custom Nov", "Custom Dec"
        };
        DateFormatSymbols.getInstance(Locale.US).setMonths(customMonths);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIScale.scale(new Dimension(800, 600)));
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        DatePicker datePicker = new DatePicker();

        JButton change = new JButton("Change");
        change.addActionListener(e -> {
            datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
        });
        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {

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
            }
        });

        datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.now();
        JFormattedTextField editor = new JFormattedTextField();
        datePicker.setEditor(editor);
        add(editor, "width 250");
        add(change);
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new TestDate().setVisible(true));
    }
}
