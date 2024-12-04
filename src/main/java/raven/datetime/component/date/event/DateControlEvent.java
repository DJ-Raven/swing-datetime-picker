package raven.datetime.component.date.event;

import java.util.EventObject;

public class DateControlEvent extends EventObject {

    public static final int DAY_STATE = 1;
    public static final int MONTH_STATE = 2;
    public static final int YEAR_STATE = 3;

    public static final int BACK = 10;
    public static final int FORWARD = 11;
    public static final int MONTH = 12;
    public static final int YEAR = 13;

    protected int state;
    protected int type;

    public DateControlEvent(Object source, int state, int type) {
        super(source);
        this.state = state;
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public int getType() {
        return type;
    }
}
