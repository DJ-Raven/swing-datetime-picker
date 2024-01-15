package raven.datetime.component.date;

public class DateSelection {

    protected DatePicker.DateSelectionMode dateSelectionMode = DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED;
    private SingleDate date;
    private SingleDate toDate;
    private SingleDate hoverDate;

    public SingleDate getDate() {
        return date;
    }

    public void setDate(SingleDate date) {
        this.date = date;
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

    protected void selectDate(SingleDate date) {
        if (dateSelectionMode == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            setDate(date);
        } else {
            if (getDate() == null) {
                setDate(date);
                setHoverDate(date);
            } else {
                setToDate(date);
            }
        }
    }
}
