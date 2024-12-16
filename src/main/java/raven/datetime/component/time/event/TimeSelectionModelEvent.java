package raven.datetime.component.time.event;

import java.util.EventObject;

public class TimeSelectionModelEvent extends EventObject {

    public static final int HOUR = 1;
    public static final int MINUTE = 2;
    public static final int HOUR_MINUTE = 3;

    protected int action;

    public TimeSelectionModelEvent(Object source, int action) {
        super(source);
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
