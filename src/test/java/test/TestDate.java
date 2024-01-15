package test;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
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
        datePicker.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:0,0,0,0,$Component.borderColor,,10");
        add(datePicker);
        JButton change = new JButton("Change");
        change.addActionListener(e -> {
            // panelTimePicker.set24HourView(!panelTimePicker.is24HourView());
        });
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
