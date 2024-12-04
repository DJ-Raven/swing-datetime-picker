package raven.datetime.component.date;

import raven.datetime.DatePicker;
import raven.datetime.DateSelectionAble;
import raven.datetime.component.date.event.DateSelectionModelEvent;
import raven.datetime.component.date.event.DateSelectionModelListener;

import javax.swing.event.EventListenerList;

public class DateSelectionModel {

    protected EventListenerList listenerList = new EventListenerList();
    private DatePicker.DateSelectionMode dateSelectionMode;
    private DateSelectionAble dateSelectionAble;
    private SingleDate date;
    private SingleDate toDate;
    private SingleDate hoverDate;

    public DateSelectionModel() {
        this(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
    }

    public DateSelectionModel(DatePicker.DateSelectionMode dateSelectionMode) {
        this(dateSelectionMode, null);
    }

    public DateSelectionModel(DateSelectionAble dateSelectionAble) {
        this(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED, dateSelectionAble);
    }

    public DateSelectionModel(DatePicker.DateSelectionMode dateSelectionMode, DateSelectionAble dateSelectionAble) {
        this.dateSelectionMode = dateSelectionMode;
        this.dateSelectionAble = dateSelectionAble;
    }

    public SingleDate getDate() {
        return date;
    }

    public void setDate(SingleDate date) {
        if (equalsDate(this.date, date)) {
            return;
        }
        if (!checkSelection(date)) {
            return;
        }
        this.date = date;
        fireDatePickerChanged(new DateSelectionModelEvent(this, DateSelectionModelEvent.DATE));
    }

    public SingleDate getToDate() {
        return toDate;
    }

    public void setToDate(SingleDate toDate) {
        if (equalsDate(this.toDate, toDate)) {
            return;
        }
        if (!checkSelection(toDate)) {
            return;
        }
        this.toDate = toDate;
    }

    public SingleDate getHoverDate() {
        return hoverDate;
    }

    public void setHoverDate(SingleDate hoverDate) {
        if (equalsDate(this.hoverDate, hoverDate)) {
            return;
        }
        this.hoverDate = hoverDate;
        fireDatePickerChanged(new DateSelectionModelEvent(this, DateSelectionModelEvent.BETWEEN_DATE_HOVER));
    }

    public void setSelectDate(SingleDate from, SingleDate to) {
        if (equalsDate(this.date, from) && equalsDate(this.toDate, to)) {
            return;
        }
        if (!checkSelection(from) || !checkSelection(to)) {
            return;
        }
        date = from;
        toDate = to;
        fireDatePickerChanged(new DateSelectionModelEvent(this, DateSelectionModelEvent.DATE));
    }

    protected void selectDate(SingleDate date) {
        if (!checkSelection(date)) {
            return;
        }
        if (dateSelectionMode == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            setDate(date);
        } else {
            if (getDate() == null || getToDate() != null) {
                this.date = date;
                hoverDate = date;
                if (getToDate() != null) {
                    this.toDate = null;
                }
                fireDatePickerChanged(new DateSelectionModelEvent(this, DateSelectionModelEvent.DATE));
            } else {
                this.toDate = date;
                invertIfNecessaryToDate(date);
                fireDatePickerChanged(new DateSelectionModelEvent(this, DateSelectionModelEvent.DATE));
            }
        }
    }

    public void setDateSelectionAble(DateSelectionAble dateSelectionAble) {
        this.dateSelectionAble = dateSelectionAble;
    }

    public DateSelectionAble getDateSelectionAble() {
        return dateSelectionAble;
    }

    public void addDatePickerSelectionListener(DateSelectionModelListener listener) {
        listenerList.add(DateSelectionModelListener.class, listener);
    }

    public void removeDatePickerSelectionListener(DateSelectionModelListener listener) {
        listenerList.remove(DateSelectionModelListener.class, listener);
    }

    public void fireDatePickerChanged(DateSelectionModelEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DateSelectionModelListener.class) {
                ((DateSelectionModelListener) listeners[i + 1]).dateSelectionModelChanged(event);
            }
        }
    }

    public boolean equalsDate(SingleDate date1, SingleDate date2) {
        // both are null
        if (date1 == null && date2 == null) {
            return true;
        }
        // only one is null
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.same(date2);
    }

    public DatePicker.DateSelectionMode getDateSelectionMode() {
        return dateSelectionMode;
    }

    public void setDateSelectionMode(DatePicker.DateSelectionMode dateSelectionMode) {
        this.dateSelectionMode = dateSelectionMode;
    }

    private boolean checkSelection(SingleDate date) {
        if (dateSelectionAble != null) {
            return date == null || dateSelectionAble.isDateSelectedAble(date.toLocalDate());
        }
        return true;
    }

    private void invertIfNecessaryToDate(SingleDate date) {
        if (this.date.toLocalDate().isAfter(date.toLocalDate())) {
            this.toDate = this.date;
            this.date = date;
        }
    }
}
