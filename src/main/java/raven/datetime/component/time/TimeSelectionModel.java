package raven.datetime.component.time;

import raven.datetime.component.time.event.TimeSelectionModelEvent;
import raven.datetime.component.time.event.TimeSelectionModelListener;

import javax.swing.event.EventListenerList;
import java.time.LocalTime;

public class TimeSelectionModel {

    protected EventListenerList listenerList = new EventListenerList();
    private int hour = -1;
    private int minute = -1;

    public TimeSelectionModel() {
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
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
            set(getDefaultSelectionHour(), minute);
        } else {
            if (this.minute != minute) {
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
        if (this.hour != hour || this.minute != minute) {
            this.hour = hour;
            this.minute = minute;
            fireTimePickerChanged(new TimeSelectionModelEvent(this, TimeSelectionModelEvent.HOUR_MINUTE));
        }
    }

    /**
     * When hour unselected and user select the minute
     * So we need set the default hour
     */
    public int getDefaultSelectionHour() {
        return 0;
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
