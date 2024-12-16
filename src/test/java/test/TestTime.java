package test;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import net.miginfocom.swing.MigLayout;
import raven.datetime.TimePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TestTime extends TestFrame {

    private TimePicker timePicker;

    public TestTime() {
        setLayout(new MigLayout("wrap"));
        timePicker = new TimePicker();
        timePicker.addTimeSelectionListener(timeEvent -> {
            if (timePicker.isTimeSelected()) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm a");
                System.out.println("event selected : " + timePicker.getSelectedTime().format(df));
            } else {
                System.out.println("event selected : null");
            }
        });
        JFormattedTextField editor = new JFormattedTextField();
        timePicker.setEditor(editor);
        add(editor, "width 200");
        createTimeOption();
    }

    private void createTimeOption() {
        JPanel panel = new JPanel(new MigLayout("wrap", "[150]"));
        panel.setBorder(new TitledBorder("Option"));
        JCheckBox ch24hourView = new JCheckBox("24 hour view");
        JCheckBox chHorizontal = new JCheckBox("Horizontal");
        ch24hourView.addActionListener(e -> {
            timePicker.set24HourView(ch24hourView.isSelected());
        });
        chHorizontal.addActionListener(e -> timePicker.setOrientation(chHorizontal.isSelected() ? SwingConstants.HORIZONTAL : SwingConstants.VERTICAL));
        panel.add(ch24hourView);
        panel.add(chHorizontal);
        add(panel);
    }


    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();
        EventQueue.invokeLater(() -> new TestTime().setVisible(true));
    }
}
