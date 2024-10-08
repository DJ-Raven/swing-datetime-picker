package raven.datetime.component.time;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.datetime.util.InputUtils;
import raven.datetime.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimePicker extends JPanel {

    private final DateTimeFormatter format12h = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
    private final DateTimeFormatter format24h = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
    private final List<TimeSelectionListener> events = new ArrayList<>();
    private TimeSelectionListener timeSelectionListener;
    private InputUtils.ValueCallback valueCallback;
    private JFormattedTextField editor;
    private Icon editorIcon;
    private JPopupMenu popupMenu;
    private MigLayout layout;
    private int orientation = SwingConstants.VERTICAL;
    private Color color;
    private LookAndFeel oldThemes = UIManager.getLookAndFeel();
    private JButton editorButton;
    private LocalTime oldSelectedTime;

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        if (this.orientation != orientation) {
            String c = orientation == SwingConstants.VERTICAL ? "wrap," : "";
            layout.setLayoutConstraints(c + "fill,insets 3");
            this.orientation = orientation;
            header.setOrientation(orientation);
            revalidate();
        }
    }

    public void setEditor(JFormattedTextField editor) {
        if (editor != this.editor) {
            if (this.editor != null) {
                uninstallEditor(this.editor);
            }
            if (editor != null) {
                installEditor(editor);
            }
            this.editor = editor;
        }
    }

    public boolean is24HourView() {
        return panelClock.isUse24hour();
    }

    public void set24HourView(boolean hour24) {
        if (panelClock.isUse24hour() != hour24) {
            panelClock.setUse24hour(hour24, header.isAm());
            header.setUse24hour(hour24);
            if (editor != null) {
                InputUtils.changeTimeFormatted(editor, hour24);
                if (isTimeSelected()) {
                    editor.setValue(getSelectedTimeAsString());
                }
                runEventTimeChanged();
            }
        }
    }

    public void showPopup() {
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
            popupMenu.putClientProperty(FlatClientProperties.STYLE, "" +
                    "borderInsets:1,1,1,1");
            popupMenu.add(this);
        }
        if (UIManager.getLookAndFeel() != oldThemes) {
            // Component in popup not update UI when change themes, so need to update when popup show
            SwingUtilities.updateComponentTreeUI(popupMenu);
            oldThemes = UIManager.getLookAndFeel();
        }
        Point point = Utils.adjustPopupLocation(popupMenu, editor);
        popupMenu.show(editor, point.x, point.y);
    }

    public void closePopup() {
        if (popupMenu != null) {
            popupMenu.setVisible(false);
            repaint();
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        header.setColor(color);
        panelClock.setColor(color);
    }

    public Icon getEditorIcon() {
        return editorIcon;
    }

    public void setEditorIcon(Icon editorIcon) {
        this.editorIcon = editorIcon;
        if (editorButton != null) {
            editorButton.setIcon(editorIcon);
        }
    }

    public TimePicker() {
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken($Panel.background,2%);" +
                "[dark]background:lighten($Panel.background,2%);");
        layout = new MigLayout("wrap,fill,insets 3", "fill", "fill");
        setLayout(layout);
        header = new Header(getEventHeader());
        panelClock = new PanelClock(getEventClock());
        add(header, "width 120:120");
        add(panelClock, "width 230:230, height 230:230");
    }

    /**
     * Set time to current local time
     */
    public void now() {
        setSelectedTime(LocalTime.now());
    }

    public void setSelectedTime(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        header.setAm(hour < 12);
        panelClock.setMinute(minute);
        panelClock.setHourAndFix(hour);
    }

    public void clearSelectedTime() {
        panelClock.setMinute(-1);
        panelClock.setHour(-1);
        panelClock.setHourSelectionView(true);
        header.clearTime();
    }

    public boolean isTimeSelected() {
        return panelClock.getHour() != -1 && panelClock.getMinute() != -1;
    }

    public LocalTime getSelectedTime() {
        int hour = panelClock.getHour();
        int minute = panelClock.getMinute();
        if (!isTimeSelected()) {
            return null;
        }
        if (panelClock.isUse24hour()) {
            return LocalTime.of(hour, minute);
        }
        if (header.isAm()) {
            if (hour == 12) {
                hour = 0;
            }
        } else {
            if (hour < 12) {
                hour += 12;
            }
        }
        return LocalTime.of(hour, minute);
    }

    public String getSelectedTimeAsString() {
        if (isTimeSelected()) {
            if (panelClock.isUse24hour()) {
                return format24h.format(getSelectedTime());
            } else {
                return format12h.format(getSelectedTime());
            }
        } else {
            return null;
        }
    }

    public void addTimeSelectionListener(TimeSelectionListener event) {
        events.add(event);
    }

    public void removeTimeSelectionListener(TimeSelectionListener event) {
        if (events != null) {
            events.remove(event);
        }
    }

    public void removeAllTimeSelectionListener() {
        if (events != null) {
            events.clear();
        }
    }

    private void installEditor(JFormattedTextField editor) {
        JToolBar toolBar = new JToolBar();
        editorButton = new JButton(editorIcon != null ? editorIcon : new FlatSVGIcon("raven/datetime/icon/clock.svg", 0.8f));
        toolBar.add(editorButton);
        editorButton.addActionListener(e -> {
            showPopup();
        });
        InputUtils.useTimeInput(editor, panelClock.isUse24hour(), getValueCallback());
        editor.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, toolBar);
        addTimeSelectionListener(getTimeSelectionListener());
    }

    private void uninstallEditor(JFormattedTextField editor) {
        if (editor != null) {
            editorButton = null;
            InputUtils.removePropertyChange(editor);
            if (timeSelectionListener != null) {
                removeTimeSelectionListener(timeSelectionListener);
            }
        }
    }

    private InputUtils.ValueCallback getValueCallback() {
        if (valueCallback == null) {
            valueCallback = value -> {
                if (value == null && isTimeSelected()) {
                    clearSelectedTime();
                } else {
                    if (value != null && !value.equals(getSelectedTimeAsString())) {
                        LocalTime time = InputUtils.stringToTime(panelClock.isUse24hour(), value.toString());
                        if (time != null) {
                            setSelectedTime(time);
                        }
                    }
                }
            };
        }
        return valueCallback;
    }

    private TimeSelectionListener getTimeSelectionListener() {
        if (timeSelectionListener == null) {
            timeSelectionListener = new TimeSelectionListener() {

                @Override
                public void timeSelected(TimeEvent timeEvent) {
                    if (isTimeSelected()) {
                        String value;
                        if (panelClock.isUse24hour()) {
                            value = format24h.format(getSelectedTime());
                        } else {
                            value = format12h.format(getSelectedTime());
                        }
                        if (!editor.getText().toLowerCase().equals(value.toLowerCase())) {
                            editor.setValue(value);
                        }
                    } else {
                        editor.setValue(null);
                    }
                }
            };
        }
        return timeSelectionListener;
    }

    private void runEventTimeChanged() {
        if (events == null || events.isEmpty()) {
            return;
        }
        LocalTime time = getSelectedTime();
        if ((time == null && oldSelectedTime == null)) {
            return;
        } else if (time != null && oldSelectedTime != null) {
            if (time.compareTo(oldSelectedTime) == 0) {
                return;
            }
        }
        oldSelectedTime = time;
        EventQueue.invokeLater(() -> {
            for (TimeSelectionListener event : events) {
                event.timeSelected(new TimeEvent(this));
            }
        });
    }

    private Header.EventHeaderChanged getEventHeader() {
        return new Header.EventHeaderChanged() {
            @Override
            public void hourMinuteChanged(boolean isHour) {
                panelClock.setHourSelectionView(isHour);
            }

            @Override
            public void amPmChanged(boolean isAm) {
                runEventTimeChanged();
            }
        };
    }

    private PanelClock.EventClockChanged getEventClock() {
        return new PanelClock.EventClockChanged() {
            @Override
            public void hourChanged(int hour) {
                header.setHour(hour);
                runEventTimeChanged();
            }

            @Override
            public void minuteChanged(int minute) {
                header.setMinute(minute);
                runEventTimeChanged();
            }

            @Override
            public void hourMinuteChanged(boolean isHour) {
                header.setHourSelect(isHour);
            }

            @Override
            public void amPmChanged(boolean isAm) {
                header.setAm(isAm);
                runEventTimeChanged();
            }
        };
    }

    private Header header;
    private PanelClock panelClock;
}
