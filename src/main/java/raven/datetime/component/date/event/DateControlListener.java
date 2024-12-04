package raven.datetime.component.date.event;

import java.util.EventListener;

public interface DateControlListener extends EventListener {

    void dateControlChanged(DateControlEvent e);
}
