package test;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;
import raven.datetime.RangeDatePicker;

import javax.swing.*;
import java.awt.*;

public class TestRangeDate extends TestFrame {

    private RangeDatePicker rangeDatePicker;

    public TestRangeDate() {
        setLayout(new MigLayout("wrap"));
        rangeDatePicker = new RangeDatePicker();

        add(rangeDatePicker);
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new TestRangeDate().setVisible(true));
    }
}
