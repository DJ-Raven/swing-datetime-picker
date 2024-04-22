package raven.datetime.component.date;

public class DateSelection {

    protected DatePicker.DateSelectionMode dateSelectionMode = DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED;
    private DateSelectionAble dateSelectionAble;
    private SingleDate date;
    private SingleDate toDate;
    private SingleDate hoverDate;

    protected final DatePicker datePicker;

    protected DateSelection(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public SingleDate getDate() {
        return date;
    }

    public void setDate(SingleDate date) {
        if (!checkSelection(date)) {
            return;
        }
        this.date = date;
        datePicker.runEventDateChanged();
    }

    public SingleDate getToDate() {
        return toDate;
    }

    public void setToDate(SingleDate toDate) {
        if (!checkSelection(toDate)) {
            return;
        }
        this.toDate = toDate;
    }

    public SingleDate getHoverDate() {
        return hoverDate;
    }

    public void setHoverDate(SingleDate hoverDate) {
        this.hoverDate = hoverDate;
    }

    protected void setSelectDate(SingleDate from, SingleDate to) {
        if (!checkSelection(from) || !checkSelection(to)) {
            return;
        }
        date = from;
        toDate = to;
        datePicker.runEventDateChanged();
    }

    protected void selectDate(SingleDate date) {
        if (!checkSelection(date)) {
            return;
        }
        if (dateSelectionMode == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            setDate(date);
            if (datePicker.isCloseAfterSelected()) {
                datePicker.closePopup();
            }
        } else {
            if (getDate() == null || getToDate() != null) {
                this.date = date;
                hoverDate = date;
                if (getToDate() != null) {
                    this.toDate = null;
                }
            } else {
                this.toDate = date;
                invertIfNecessaryToDate(date);
                datePicker.runEventDateChanged();
                if (datePicker.isCloseAfterSelected()) {
                    datePicker.closePopup();
                }
            }
        }
    }

    private void invertIfNecessaryToDate(SingleDate date) {
        if (this.date.toLocalDate().isAfter(date.toLocalDate())) {
            this.toDate = this.date;
            this.date = date;
        }
    }

    public void setDateSelectionAble(DateSelectionAble dateSelectionAble) {
        this.dateSelectionAble = dateSelectionAble;
    }

    public DateSelectionAble getDateSelectionAble() {
        return dateSelectionAble;
    }

    private boolean checkSelection(SingleDate date) {
        if (dateSelectionAble != null) {
            return date == null || dateSelectionAble.isDateSelectedAble(date.toLocalDate());
        }
        return true;
    }
}
