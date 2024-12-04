package raven.datetime.event;

import java.util.EventListener;

public interface DateSelectionListener extends EventListener {

    void dateSelected(DateSelectionEvent dateSelectionEvent);
}
