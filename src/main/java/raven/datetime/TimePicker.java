package raven.datetime;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.PanelPopupEditor;
import raven.datetime.component.time.Header;
import raven.datetime.component.time.PanelClock;
import raven.datetime.event.TimeSelectionEvent;
import raven.datetime.event.TimeSelectionListener;
import raven.datetime.util.InputUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimePicker extends PanelPopupEditor {

    private final DateTimeFormatter format12h = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
    private final DateTimeFormatter format24h = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
    private TimeSelectionListener timeSelectionListener;
    private InputUtils.ValueCallback valueCallback;
    private Icon editorIcon;
    private MigLayout layout;
    private int orientation = SwingConstants.VERTICAL;
    private Color color;
    private JButton editorButton;
    private LocalTime oldSelectedTime;

    private Header header;
    private PanelClock panelClock;

    public TimePicker() {
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken($Panel.background,2%);" +
                "[dark]background:lighten($Panel.background,2%);");
        layout = new MigLayout(
                "wrap,fill,insets 3",
                "fill",
                "fill");
        setLayout(layout);
        header = new Header(getEventHeader());
        panelClock = new PanelClock(getEventClock());
        add(header, "width 120:120");
        add(panelClock, "width 230:230, height 230:230");
    }

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
            this.editor = editor;
            if (editor != null) {
                installEditor(editor);
            }
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
                setEditorValue();
            }
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

    public void addTimeSelectionListener(TimeSelectionListener listener) {
        listenerList.add(TimeSelectionListener.class, listener);
    }

    public void removeTimeSelectionListener(TimeSelectionListener listener) {
        listenerList.remove(TimeSelectionListener.class, listener);
    }

    private void installEditor(JFormattedTextField editor) {
        JToolBar toolBar = new JToolBar();
        editorButton = new JButton(editorIcon != null ? editorIcon : new FlatSVGIcon("raven/datetime/icon/clock.svg", 0.8f));
        toolBar.add(editorButton);
        editorButton.addActionListener(e -> {
            editor.grabFocus();
            showPopup();
        });
        InputUtils.useTimeInput(editor, panelClock.isUse24hour(), getValueCallback());
        setEditorValue();
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
            timeSelectionListener = timeSelectionEvent -> {
                setEditorValue();
            };
        }
        return timeSelectionListener;
    }

    private void setEditorValue() {
        String value = getSelectedTimeAsString();
        if (value != null) {
            if (!editor.getText().toLowerCase().equals(value.toLowerCase())) {
                editor.setValue(value);
            }
        } else {
            editor.setValue(null);
        }
    }

    private void verifyTimeSelection() {
        LocalTime time = getSelectedTime();
        if ((time == null && oldSelectedTime == null)) {
            return;
        } else if (time != null && oldSelectedTime != null) {
            if (time.compareTo(oldSelectedTime) == 0) {
                return;
            }
        }
        oldSelectedTime = time;
        fireTimeSelectionChanged(new TimeSelectionEvent(this));
    }

    public void fireTimeSelectionChanged(TimeSelectionEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TimeSelectionListener.class) {
                ((TimeSelectionListener) listeners[i + 1]).timeSelected(event);
            }
        }
    }

    private Header.EventHeaderChanged getEventHeader() {
        return new Header.EventHeaderChanged() {
            @Override
            public void hourMinuteChanged(boolean isHour) {
                panelClock.setHourSelectionView(isHour);
            }

            @Override
            public void amPmChanged(boolean isAm) {
                verifyTimeSelection();
            }
        };
    }

    private PanelClock.EventClockChanged getEventClock() {
        return new PanelClock.EventClockChanged() {
            @Override
            public void hourChanged(int hour) {
                header.setHour(hour);
                verifyTimeSelection();
            }

            @Override
            public void minuteChanged(int minute) {
                header.setMinute(minute);
                verifyTimeSelection();
            }

            @Override
            public void hourMinuteChanged(boolean isHour) {
                header.setHourSelect(isHour);
            }

            @Override
            public void amPmChanged(boolean isAm) {
                header.setAm(isAm);
                verifyTimeSelection();
            }
        };
    }
}
