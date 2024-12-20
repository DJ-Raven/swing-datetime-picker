package raven.datetime.component.time;

import raven.datetime.TimeSelectionAble;
import raven.datetime.component.time.event.TimeSelectionModelEvent;
import raven.datetime.component.time.event.TimeSelectionModelListener;

import javax.swing.event.EventListenerList;
import java.time.LocalTime;

public class TimeSelectionModel {

    protected EventListenerList listenerList = new EventListenerList();
    private TimeSelectionAble timeSelectionAble;
    private int hour = -1;
    private int minute = -1;

    public TimeSelectionModel() {
    }

    public TimeSelectionAble getTimeSelectionAble() {
        return timeSelectionAble;
    }

    public void setTimeSelectionAble(TimeSelectionAble timeSelectionAble) {
        this.timeSelectionAble = timeSelectionAble;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        if (!checkSelection(hour, minute)) {
            return;
        }
        if (this.hour != hour) {
            this.hour = hour;
            fireTimePickerChanged(new TimeSelectionModelEvent(this, TimeSelectionModelEvent.HOUR));
        }
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        if (hour == -1) {
            set(getDefaultSelectionHour(minute), minute);
        } else {
            if (this.minute != minute) {
                if (!checkSelection(hour, minute)) {
                    return;
                }
                this.minute = minute;
                fireTimePickerChanged(new TimeSelectionModelEvent(this, TimeSelectionModelEvent.MINUTE));
            }
        }
    }

    public boolean isSelected() {
        return hour != -1 && minute != -1;
    }

    public LocalTime getTime() {
        if (isSelected()) {
            return LocalTime.of(hour, minute);
        }
        return null;
    }

    public void set(int hour, int minute) {
        int action = 0;
        if (this.hour != hour && this.minute != minute) {
            action = TimeSelectionModelEvent.HOUR_MINUTE;
        } else if (this.hour != hour) {
            action = TimeSelectionModelEvent.HOUR;
        } else if (this.minute != minute) {
            action = TimeSelectionModelEvent.MINUTE;
        }
        if (action != 0) {
            if (!checkSelection(hour, minute)) {
                return;
            }
            this.hour = hour;
            this.minute = minute;
            fireTimePickerChanged(new TimeSelectionModelEvent(this, action));
        }
    }

    /**
     * When hour unselected and user select the minute
     * So we need set the default hour
     */
    public int getDefaultSelectionHour(int minute) {
        int defaultHour = 0;
        if (timeSelectionAble != null) {
            defaultHour = getAvailableDefaultHour(defaultHour, minute);
        }
        return defaultHour;
    }

    /**
     * Default hour can be disabled by timeSelectionAble
     * Use this method to get the available hour
     */
    private int getAvailableDefaultHour(int startHour, int minute) {
        int hour = startHour;
        for (int i = 0; i < 23; i++) {
            if (checkSelection(hour, minute)) {
                return hour;
            }
            hour++;
            if (hour == 24) {
                hour = 0;
            }
        }
        return startHour;
    }

    /**
     * When check time selection able but minute not set
     * Use this for default minute to check the available hour
     */
    private int getDefaultMinuteCheck() {
        return 0;
    }

    public boolean checkSelection(int hour, int minute) {
        if (timeSelectionAble == null || hour == -1 || (minute == -1 && hour == -1)) {
            return true;
        }
        // hourView true if only hour are set. use to display the hour on the PanelClock
        boolean hourView = false;
        if (minute == -1) {
            minute = getDefaultMinuteCheck();
            hourView = true;
        }
        return timeSelectionAble.isTimeSelectedAble(LocalTime.of(hour, minute), hourView);
    }

    public void addTimePickerSelectionListener(TimeSelectionModelListener listener) {
        listenerList.add(TimeSelectionModelListener.class, listener);
    }

    public void removeTimePickerSelectionListener(TimeSelectionModelListener listener) {
        listenerList.remove(TimeSelectionModelListener.class, listener);
    }

    public void fireTimePickerChanged(TimeSelectionModelEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TimeSelectionModelListener.class) {
                ((TimeSelectionModelListener) listeners[i + 1]).timeSelectionModelChanged(event);
            }
        }
    }
}
