package raven.datetime.component.time.event;

import java.util.EventListener;

public interface TimeSelectionListener extends EventListener {

    void timeSelected(TimeSelectionEvent timeSelectionEvent);
}
