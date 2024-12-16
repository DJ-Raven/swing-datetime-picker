package test;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {

    public TestFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        createMenuBar();
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
}
