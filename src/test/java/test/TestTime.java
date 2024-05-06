package test;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.time.TimeEvent;
import raven.datetime.component.time.TimePicker;
import raven.datetime.component.time.TimeSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TestTime extends JFrame {

    public TestTime() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIScale.scale(new Dimension(800, 600)));
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        TimePicker timePicker = new TimePicker();
        timePicker.addTimeSelectionListener(new TimeSelectionListener() {
            @Override
            public void timeSelected(TimeEvent timeEvent) {
                if (timePicker.isTimeSelected()) {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm a");
                    System.out.println("event selected : " + timePicker.getSelectedTime().format(df));
                } else {
                    System.out.println("event selected : null");
                }
            }
        });
        JFormattedTextField editor = new JFormattedTextField();
        timePicker.setEditor(editor);
        JButton change = new JButton("Use 12h");
        change.addActionListener(e -> {
            if (change.getText().equals("Use 24h")) {
                change.setText("Use 12h");
                timePicker.set24HourView(false);
            } else {
                change.setText("Use 24h");
                timePicker.set24HourView(true);
            }
        });
        add(editor, "width 200");
        add(change);
        timePicker.setOrientation(SwingConstants.HORIZONTAL);

        // createThemeButton();
        //timePicker.setColor(new Color(20, 161, 108));
        timePicker.now();
    }

    private void createThemeButton() {
        JButton cmd = new JButton("Change themes");
        cmd.addActionListener(e -> {
            if (FlatLaf.isLafDark()) {
                EventQueue.invokeLater(() -> {
                    FlatAnimatedLafChange.showSnapshot();
                    FlatMacLightLaf.setup();
                    FlatLaf.updateUI();
                    FlatAnimatedLafChange.hideSnapshotWithAnimation();
                });
            } else {
                EventQueue.invokeLater(() -> {
                    FlatAnimatedLafChange.showSnapshot();
                    FlatMacDarkLaf.setup();
                    FlatLaf.updateUI();
                    FlatAnimatedLafChange.hideSnapshotWithAnimation();
                });
            }
        });
        add(cmd);
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new TestTime().setVisible(true));
    }
}
