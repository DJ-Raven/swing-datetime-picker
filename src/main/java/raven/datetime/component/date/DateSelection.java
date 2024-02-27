package raven.datetime.component.date;

public class DateSelection {

    protected DatePicker.DateSelectionMode dateSelectionMode = DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED;
    private SingleDate date;
    private SingleDate toDate;
    private SingleDate hoverDate;

    private final DatePicker datePicker;

    protected DateSelection(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public SingleDate getDate() {
        return date;
    }

    public void setDate(SingleDate date) {
        this.date = date;
        datePicker.runEventDateChanged();
    }

    public SingleDate getToDate() {
        return toDate;
    }

    public void setToDate(SingleDate toDate) {
        this.toDate = toDate;
    }

    public SingleDate getHoverDate() {
        return hoverDate;
    }

    public void setHoverDate(SingleDate hoverDate) {
        this.hoverDate = hoverDate;
    }

    protected void setSelectDate(SingleDate from, SingleDate to) {
        date = from;
        toDate = to;
        datePicker.runEventDateChanged();
    }

    protected void selectDate(SingleDate date) {
        if (dateSelectionMode == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            setDate(date);
        } else {
            if (getDate() == null || toDate != null) {
                this.date = date;
                hoverDate = date;
                if (toDate != null) {
                    toDate = null;
                }
            } else {
                toDate = date;
                datePicker.runEventDateChanged();
            }
        }
    }
}
