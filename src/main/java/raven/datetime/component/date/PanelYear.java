package raven.datetime.component.date;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class PanelYear extends JPanel {

    public static final int YEAR_CELL = 28;
    private final EventYearChanged yearChanged;
    private final DateSelection dateSelection;
    private final int year;

    public PanelYear(EventYearChanged yearChanged, DateSelection dateSelection, int year) {
        this.yearChanged = yearChanged;
        this.dateSelection = dateSelection;
        this.year = year;
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap 4,insets 3,fillx,gap 3,al center center", "fill,sg main", "fill"));
        int count = YEAR_CELL;
        for (int i = 0; i < count; i++) {
            final int y = getStartYear(year) + i;
            ButtonMonthYear button = new ButtonMonthYear(dateSelection, i, true);
            button.setText(y + "");
            button.addActionListener(e -> {
                yearChanged.yearSelected(y);
            });
            add(button);
        }
    }

    private int getStartYear(int year) {
        int initYear = 1900;
        int currentYear = year;
        int yearsPerPage = YEAR_CELL;
        int yearsPassed = currentYear - initYear;
        int pages = yearsPassed / yearsPerPage;
        int startingYearOnPage = initYear + (pages * yearsPerPage);
        return startingYearOnPage;
    }

    public int getYear() {
        return year;
    }

    public interface EventYearChanged {

        void yearSelected(int year);
    }
}
