package test;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDate extends TestFrame {

    private DatePicker datePicker;

    public TestDate() {
        setLayout(new MigLayout("wrap"));
        datePicker = new DatePicker();
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

        datePicker.setDateSelectionAble((date) -> !date.isAfter(LocalDate.now()));

        datePicker.now();
        JFormattedTextField editor = new JFormattedTextField();
        datePicker.setEditor(editor);
        add(editor, "width 250");
        createDateOption();
    }

    private void createDateOption() {
        JPanel panel = new JPanel(new MigLayout("wrap"));
        panel.setBorder(new TitledBorder("Option"));
        JCheckBox chBtw = new JCheckBox("Use Date between");
        chBtw.addActionListener(e -> {
            if (chBtw.isSelected()) {
                datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
            } else {
                datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
            }
        });
        JCheckBox chDateOpt = new JCheckBox("Use Panel Option");
        chDateOpt.addActionListener(e -> datePicker.setUsePanelOption(chDateOpt.isSelected()));
        JCheckBox chClose = new JCheckBox("Close after selected");
        chClose.addActionListener(e -> datePicker.setCloseAfterSelected(chClose.isSelected()));

        JCheckBox chEditorValidation = new JCheckBox("Editor validation", datePicker.isEditorValidation());
        JCheckBox chValidationOnNull = new JCheckBox("Validation on null");
        JCheckBox chAnimationEnabled = new JCheckBox("Animation Enabled", datePicker.isAnimationEnabled());

        chEditorValidation.addActionListener(e -> {
            datePicker.setEditorValidation(chEditorValidation.isSelected());
            chValidationOnNull.setEnabled(chEditorValidation.isSelected());
        });

        chValidationOnNull.addActionListener(e -> datePicker.setValidationOnNull(chValidationOnNull.isSelected()));
        chAnimationEnabled.addActionListener(e -> datePicker.setAnimationEnabled(chAnimationEnabled.isSelected()));

        panel.add(chBtw);
        panel.add(chDateOpt);
        panel.add(chClose);
        panel.add(chEditorValidation);
        panel.add(chValidationOnNull);
        panel.add(chAnimationEnabled);
        add(panel);
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new TestDate().setVisible(true));
    }
}
