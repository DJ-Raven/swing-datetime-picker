package raven.datetime.component.date;

import java.time.LocalDate;
import java.util.Calendar;

public class SingleDate {

    private int day;
    private int month;
    private int year;

    public SingleDate() {
        this(LocalDate.now());
    }

    public SingleDate(LocalDate date) {
        this.day = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public SingleDate(Calendar calendar) {
        this(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    public SingleDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean same(SingleDate date) {
        return date != null && year == date.year && month == date.month && day == date.day;
    }

    public boolean after(SingleDate date) {
        if (date == null) {
            return false;
        }
        if (year > date.year) {
            return true;
        } else if (year < date.year) {
            return false;
        } else {
            if (month > date.month) {
                return true;
            } else if (month < date.month) {
                return false;
            } else {
                return day > date.day;
            }
        }
    }

    public boolean before(SingleDate date) {
        if (date == null) {
            return false;
        }
        if (year < date.year) {
            return true;
        } else if (year > date.year) {
            return false;
        } else {
            if (month < date.month) {
                return true;
            } else if (month > date.month) {
                return false;
            } else {
                return day < date.day;
            }
        }
    }

    public boolean between(SingleDate from, SingleDate to) {
        if (from.before(to)) {
            return after(from) && before(to);
        } else {
            return after(to) && before(from);
        }
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}
