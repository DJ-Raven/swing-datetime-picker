package raven.datetime.component.time;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.datetime.TimePicker;
import raven.datetime.component.time.event.TimeActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;

public class Header extends JComponent {

    private final TimePicker timePicker;
    private final TimeActionListener timeActionListener;
    private final DecimalFormat format = new DecimalFormat("00");
    private MigLayout layout;
    private Color color;

    public Header(TimePicker timePicker, TimeActionListener timeActionListener) {
        this.timePicker = timePicker;
        this.timeActionListener = timeActionListener;
        init();
    }

    private void init() {
        setOpaque(true);
        layout = new MigLayout("fill,insets 10", "center");
        setLayout(layout);
        add(createToolBar(), "id b1");
        add(createAmPm(), "pos b1.x2+rel 0.5al n n");
    }

    public void updateHeader() {
        int hour = timePicker.getTimeSelectionModel().getHour();
        int minute = timePicker.getTimeSelectionModel().getMinute();

        if (hour == -1 && minute == -1) {
            buttonAm.setSelected(false);
            buttonPm.setSelected(false);
        } else {
            if (hour >= 12) {
                buttonAm.setSelected(false);
                buttonPm.setSelected(true);
            } else {
                buttonAm.setSelected(true);
                buttonPm.setSelected(false);
            }
        }

        if (!timePicker.is24HourView()) {
            if (hour >= 12) {
                hour -= 12;
            }
            if (hour == 0) {
                hour = 12;
            }
        }

        String hourText = hour == -1 ? "--" : format.format(hour);
        String minuteText = minute == -1 ? "--" : format.format(minute);
        buttonHour.setText(hourText);
        buttonMinute.setText(minuteText);
    }

    public void setOrientation(int orientation) {
        String c = orientation == SwingConstants.VERTICAL ? "pos b1.x2+rel 0.5al n n" : "pos 0.5al b1.y2+rel n n";
        amPmToolBar.setOrientation(orientation);
        layout.setComponentConstraints(amPmToolBar, c);
    }

    public boolean isAm() {
        return !buttonPm.isSelected();
    }

    public void setHourSelectionView(boolean hourSelectionView) {
        if (hourSelectionView) {
            buttonHour.setSelected(true);
        } else {
            buttonMinute.setSelected(true);
        }
    }

    public void setUse24hour(boolean use24hour) {
        amPmToolBar.setVisible(!use24hour);
    }

    protected JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;" +
                "hoverButtonGroupBackground:null;");
        buttonHour = createButton();
        buttonMinute = createButton();
        ButtonGroup group = new ButtonGroup();
        group.add(buttonHour);
        group.add(buttonMinute);
        buttonHour.setSelected(true);
        buttonHour.addActionListener(e -> timeActionListener.selectionViewChanged(true));
        buttonMinute.addActionListener(e -> timeActionListener.selectionViewChanged(false));
        toolBar.add(buttonHour);
        toolBar.add(createSplit());
        toolBar.add(buttonMinute);
        return toolBar;
    }

    protected JToggleButton createButton() {
        JToggleButton button = new JToggleButton("--");
        button.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+15;" +
                "toolbar.margin:3,5,3,5;" +
                "foreground:contrast($Component.accentColor,$ToggleButton.background,#fff);" +
                "background:null;" +
                "toolbar.hoverBackground:null");
        return button;
    }

    protected JButton createAmPmButton(boolean isAm) {
        String[] amPM = DateFormatSymbols.getInstance().getAmPmStrings();
        String amOrPm = isAm ? amPM[0] : amPM[1];
        JButton button = new JButton(amOrPm);
        button.addActionListener(e -> {
            TimeSelectionModel timeSelectionModel = timePicker.getTimeSelectionModel();
            int hour = timeSelectionModel.getHour();
            int minute = timeSelectionModel.getMinute();
            if (isAm) {
                if (hour >= 12) {
                    hour -= 12;
                }
            } else {
                if (hour < 12) {
                    hour += 12;
                }
            }
            timeSelectionModel.set(hour, minute);
        });
        button.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+1;" +
                "foreground:contrast($Component.accentColor,$ToggleButton.background,#fff);" +
                "background:null;" +
                "toolbar.hoverBackground:null");
        return button;
    }

    protected JLabel createSplit() {
        JLabel label = new JLabel(":");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+10;" +
                "foreground:contrast($Component.accentColor,$Label.background,#fff)");
        return label;
    }

    protected JToolBar createAmPm() {
        amPmToolBar = new JToolBar();
        amPmToolBar.setOrientation(SwingConstants.VERTICAL);
        amPmToolBar.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;" +
                "hoverButtonGroupBackground:null");
        buttonAm = createAmPmButton(true);
        buttonPm = createAmPmButton(false);
        amPmToolBar.add(buttonAm);
        amPmToolBar.add(buttonPm);
        return amPmToolBar;
    }

    /**
     * Override this method to paint the background color
     * Do not use the component background because the background reset while change themes
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Color color = this.color;
        if (color == null) {
            color = UIManager.getColor("Component.accentColor");
        }
        g2.setColor(color);
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        g2.dispose();
        super.paintComponent(g);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Override this method to return the background color to the JToolBar
     * When JToolBar use null background, so it will paint the parent background.
     */
    @Override
    public Color getBackground() {
        if (color != null) {
            return color;
        }
        return UIManager.getColor("Component.accentColor");
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        buttonAm.setEnabled(enabled);
        buttonPm.setEnabled(enabled);
    }

    private JToggleButton buttonHour;
    private JToggleButton buttonMinute;

    private JToolBar amPmToolBar;
    private JButton buttonAm;
    private JButton buttonPm;
}
