package raven.datetime.component.date.event;

import java.util.EventListener;

public interface DateSelectionModelListener extends EventListener {

    void dateSelectionModelChanged(DateSelectionModelEvent e);
}
