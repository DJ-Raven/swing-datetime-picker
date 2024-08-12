package test;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDate extends JFrame {

    private DatePicker datePicker;

    public TestDate() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIScale.scale(new Dimension(800, 600)));
        createMenuBar();
        setLocationRelativeTo(null);
        setLayout(new MigLayout("wrap"));
        datePicker = new DatePicker();
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

        datePicker.setDateSelectionAble((date) -> !date.isAfter(LocalDate.now()));
        datePicker.now();
        JFormattedTextField editor = new JFormattedTextField();
        datePicker.setEditor(editor);
        add(editor, "width 250");
        createDateOption();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuThemes = new JMenu("Themes");
        JMenuItem menuExit = new JMenuItem("Exit");
        ButtonGroup group = new ButtonGroup();
        JCheckBoxMenuItem menuMacDark = new JCheckBoxMenuItem("Mac Dark");
        JCheckBoxMenuItem menuMacLight = new JCheckBoxMenuItem("Mac Light");
        group.add(menuMacDark);
        group.add(menuMacLight);

        menuMacDark.setSelected(true);

        menuExit.addActionListener(e -> System.exit(0));

        menuFile.add(menuExit);
        for (FlatAllIJThemes.FlatIJLookAndFeelInfo themeInfo : FlatAllIJThemes.INFOS) {
            menuThemes.add(createThemeButton(group, themeInfo));
        }
        menuBar.add(menuFile);
        menuBar.add(menuThemes);
        setJMenuBar(menuBar);
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

        JCheckBox chEditorValidation = new JCheckBox("Editor validation", true);
        JCheckBox chValidationOnNull = new JCheckBox("Validation on null");
        chEditorValidation.addActionListener(e -> {
            datePicker.setEditorValidation(chEditorValidation.isSelected());
            chValidationOnNull.setEnabled(chEditorValidation.isSelected());
        });

        chValidationOnNull.addActionListener(e -> datePicker.setValidationOnNull(chValidationOnNull.isSelected()));

        panel.add(chBtw);
        panel.add(chDateOpt);
        panel.add(chClose);
        panel.add(chEditorValidation);
        panel.add(chValidationOnNull);
        add(panel);
    }

    private JMenuItem createThemeButton(ButtonGroup group, FlatAllIJThemes.FlatIJLookAndFeelInfo themeInfo) {
        JCheckBoxMenuItem menu = new JCheckBoxMenuItem(themeInfo.getName());
        menu.addActionListener(e -> changeTheme(themeInfo));
        group.add(menu);
        return menu;
    }

    private void changeTheme(FlatAllIJThemes.FlatIJLookAndFeelInfo themeInfo) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            try {
                UIManager.setLookAndFeel(themeInfo.getClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            FlatLaf.updateUI();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new TestDate().setVisible(true));
    }
}
