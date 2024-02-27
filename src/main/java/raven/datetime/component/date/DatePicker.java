package raven.datetime.component.date;

import net.miginfocom.swing.MigLayout;
import raven.swing.slider.PanelSlider;
import raven.swing.slider.SimpleTransition;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatePicker extends JPanel {

    private final List<DateSelectionListener> events = new ArrayList<>();
    private final DateSelection dateSelection = new DateSelection(this);
    private PanelMonth.EventMonthChanged eventMonthChanged;
    private PanelYear.EventYearChanged eventYearChanged;
    private int month = 10;
    private int year = 2023;

    /**
     * 0 as Date select
     * 1 as Month select
     * 2 as Year select
     */
    private int panelSelect = 0;

    public DatePicker() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap,insets 10,fill", "[fill]"));
        panelSlider = new PanelSlider();
        header = new Header(getEventHeader());
        eventMonthChanged = createEventMonthChanged();
        eventYearChanged = createEventYearChanged();
        add(header);
        add(panelSlider, "width 260,height 250");
        initDate();
        add(new PanelDateOption(this), "dock east,gap 0 10 10 10");
    }

    private Header.EventHeaderChanged getEventHeader() {
        return new Header.EventHeaderChanged() {

            @Override
            public void back() {
                setToBack();
            }

            @Override
            public void forward() {
                setToForward();
            }

            @Override
            public void monthSelected() {
                selectMonth();
            }

            @Override
            public void yearSelected() {
                selectYear();
            }
        };
    }

    private PanelMonth.EventMonthChanged createEventMonthChanged() {
        return new PanelMonth.EventMonthChanged() {
            @Override
            public void monthSelected(int month) {
                DatePicker.this.month = month;
                header.setDate(month, year);
                panelSlider.addSlide(createPanelDate(month, year), SimpleTransition.get(SimpleTransition.SliderType.DOWN_TOP));
                panelSelect = 0;
            }
        };
    }

    public PanelYear.EventYearChanged createEventYearChanged() {
        return new PanelYear.EventYearChanged() {
            @Override
            public void yearSelected(int year) {
                DatePicker.this.year = year;
                header.setDate(month, year);
                panelSlider.addSlide(createPanelMonth(month, year), SimpleTransition.get(SimpleTransition.SliderType.DOWN_TOP));
                panelSelect = 1;
            }
        };
    }

    public void setToBack() {
        if (panelSelect == 0) {
            if (month == 0) {
                month = 11;
                year--;
            } else {
                month--;
            }
            header.setDate(month, year);
            panelSlider.addSlide(createPanelDate(month, year), SimpleTransition.get(SimpleTransition.SliderType.BACK));
        } else if (panelSelect == 1) {
            year--;
            header.setDate(month, year);
            panelSlider.addSlide(createPanelMonth(month, year), SimpleTransition.get(SimpleTransition.SliderType.BACK));
        } else {
            PanelYear panelYear = (PanelYear) panelSlider.getComponent(1);
            panelSlider.addSlide(createPanelYear(panelYear.getYear() - PanelYear.YEAR_CELL), SimpleTransition.get(SimpleTransition.SliderType.BACK));
        }
    }

    public void setToForward() {
        if (panelSelect == 0) {
            if (month == 11) {
                month = 0;
                year++;
            } else {
                month++;
            }
            header.setDate(month, year);
            panelSlider.addSlide(createPanelDate(month, year), SimpleTransition.get(SimpleTransition.SliderType.FORWARD));
        } else if (panelSelect == 1) {
            year++;
            header.setDate(month, year);
            panelSlider.addSlide(createPanelMonth(month, year), SimpleTransition.get(SimpleTransition.SliderType.FORWARD));
        } else {
            PanelYear panelYear = (PanelYear) panelSlider.getComponent(1);
            panelSlider.addSlide(createPanelYear(panelYear.getYear() + PanelYear.YEAR_CELL), SimpleTransition.get(SimpleTransition.SliderType.FORWARD));
        }
    }

    public void selectMonth() {
        if (panelSelect != 1) {
            panelSlider.addSlide(createPanelMonth(month, year), SimpleTransition.get(panelSelect == 0 ? SimpleTransition.SliderType.TOP_DOWN : SimpleTransition.SliderType.DOWN_TOP));
            panelSelect = 1;
        } else {
            panelSlider.addSlide(createPanelDate(month, year), SimpleTransition.get(SimpleTransition.SliderType.DOWN_TOP));
            panelSelect = 0;
        }
    }

    public void selectYear() {
        if (panelSelect != 2) {
            panelSlider.addSlide(createPanelYear(year), SimpleTransition.get(SimpleTransition.SliderType.TOP_DOWN));
            panelSelect = 2;
        } else {
            panelSlider.addSlide(createPanelDate(month, year), SimpleTransition.get(SimpleTransition.SliderType.DOWN_TOP));
            panelSelect = 0;
        }
    }

    private PanelDate createPanelDate(int month, int year) {
        return new PanelDate(dateSelection, month, year);
    }

    private PanelMonth createPanelMonth(int month, int year) {
        return new PanelMonth(eventMonthChanged, dateSelection, month, year);
    }

    private PanelYear createPanelYear(int year) {
        return new PanelYear(eventYearChanged, dateSelection, year);
    }

    protected void runEventDateChanged() {
        SwingUtilities.invokeLater(() -> {
            for (DateSelectionListener event : events) {
                event.dateSelected(new DateEvent(this));
            }
        });
    }

    private Header header;
    private PanelSlider panelSlider;

    public DateSelectionMode getDateSelectionMode() {
        return dateSelection.dateSelectionMode;
    }

    public void setDateSelectionMode(DateSelectionMode dateSelectionMode) {
        this.dateSelection.dateSelectionMode = dateSelectionMode;
    }

    private void initDate() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue() - 1;
        int year = date.getYear();
        this.month = month;
        this.year = year;
        header.setDate(month, year);
        panelSlider.addSlide(createPanelDate(month, year), null);
    }

    public void now() {
        LocalDate date = LocalDate.now();
        if (dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED) {
            setSelectedDateRange(date, date);
        } else {
            setSelectedDate(date);
        }
    }

    public void setSelectedDate(LocalDate date) {
        dateSelection.setDate(new SingleDate(date));
        if (dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED) {
            dateSelection.setToDate(new SingleDate(date));
        }
        panelSlider.repaint();
        slideTo(date);
    }

    public void setSelectedDateRange(LocalDate from, LocalDate to) {
        if (dateSelection.dateSelectionMode == DateSelectionMode.SINGLE_DATE_SELECTED) {
            throw new IllegalArgumentException("Single date mode can't accept the range date");
        }
        dateSelection.setSelectDate(new SingleDate(from), new SingleDate(to));
        panelSlider.repaint();
        slideTo(from);
    }

    public void clearSelectedDate() {
        dateSelection.setSelectDate(null, null);
        panelSlider.repaint();
    }

    public LocalDate getSelectedDate() {
        SingleDate date = dateSelection.getDate();
        if (date != null) {
            return date.toLocalDate();
        }
        return null;
    }

    public LocalDate[] getSelectedDateRange() {
        SingleDate from = dateSelection.getDate();
        if (from != null) {
            LocalDate[] dates = new LocalDate[2];
            dates[0] = from.toLocalDate();
            SingleDate to = dateSelection.getToDate();
            if (to != null) {
                dates[1] = to.toLocalDate();
                return dates;
            }
        }
        return null;
    }

    public void slideTo(LocalDate date) {
        int m = date.getMonthValue() - 1;
        int y = date.getYear();
        if (year != y || month != m) {
            if (year < y || (year <= y && month < m)) {
                panelSlider.addSlide(createPanelDate(m, y), SimpleTransition.get(SimpleTransition.SliderType.FORWARD));
            } else {
                panelSlider.addSlide(createPanelDate(m, y), SimpleTransition.get(SimpleTransition.SliderType.BACK));
            }
            month = m;
            year = y;
            panelSelect = 0;
            header.setDate(month, year);
        } else {
            if (panelSelect != 0) {
                panelSlider.addSlide(createPanelDate(m, y), SimpleTransition.get(SimpleTransition.SliderType.DOWN_TOP));
                panelSelect = 0;
            }
        }
    }

    public void addDateSelectionListener(DateSelectionListener event) {
        events.add(event);
    }

    public void removeDateSelectionListener(DateSelectionListener event) {
        if (events != null) {
            events.remove(event);
        }
    }

    public void removeAllDateSelectionListener() {
        if (events != null) {
            events.clear();
        }
    }

    public enum DateSelectionMode {
        SINGLE_DATE_SELECTED, BETWEEN_DATE_SELECTED
    }
}
