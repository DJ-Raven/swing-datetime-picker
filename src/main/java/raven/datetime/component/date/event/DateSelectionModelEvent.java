package raven.datetime.component.date.event;

import java.util.EventObject;

public class DateSelectionModelEvent extends EventObject {

    public static final int BETWEEN_DATE_HOVER = 1;
    public static final int DATE = 2;

    protected int action;

    public DateSelectionModelEvent(Object source, int action) {
        super(source);
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
