package raven.datetime;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.PanelPopupEditor;
import raven.datetime.component.date.Header;
import raven.datetime.component.date.event.DateControlEvent;
import raven.datetime.component.date.event.DateControlListener;

import javax.swing.*;
import java.time.LocalDate;

public class RangeDatePicker extends PanelPopupEditor {

    private DatePicker datePicker1;
    private DatePicker datePicker2;

    public RangeDatePicker() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken($Panel.background,2%);" +
                "[dark]background:lighten($Panel.background,2%);");
        setLayout(new MigLayout("insets 0"));
        datePicker1 = new DatePicker();
        datePicker1.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker2 = new DatePicker(datePicker1.getDateSelectionModel());

        DateControlListener controlListener = e -> {
            if (e.getType() == DateControlEvent.BACK || e.getType() == DateControlEvent.FORWARD) {
                SwingUtilities.invokeLater(() -> dateChanged((Header) e.getSource()));
            }
        };
        datePicker1.getHeader().addDateControlListener(controlListener);
        datePicker2.getHeader().addDateControlListener(controlListener);

        dateChanged(datePicker1.getHeader());
        add(datePicker1);
        add(datePicker2);
    }

    private void dateChanged(Header header) {
        int year = header.getYear();
        int month = header.getMonth();

        if (datePicker1.getHeader() == header) {
            LocalDate date = LocalDate.of(year, month + 1, 1).plusMonths(1);
            datePicker2.slideTo(date);
        } else {
            LocalDate date = LocalDate.of(year, month + 1, 1).minusMonths(1);
            datePicker1.slideTo(date);
        }
    }

    @Override
    protected String getDefaultPlaceholder() {
        return null;
    }
}
