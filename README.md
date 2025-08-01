# Swing Datetime Picker
A simple datetime picker implementation using Java Swing, built with the `flatlaf` UI library and `miglayout` for layout management.

This project provides a datetime picker component that can be easily integrated into Java Swing applications. It leverages flatlaf for a modern look and miglayout for flexible and easy-to-use layout management.

<img src="screenshot/timepicker-dark.png" alt="timepicker dark" width="300"/>&nbsp;
<img src="screenshot/timepicker-light.png" alt="timepicker light" width="300"/>
<br/>
<img src="screenshot/datepicker-dark.png" alt="datepicker dark" width="300"/>&nbsp;
<img src="screenshot/datepicker-light.png" alt="datepicker light" width="300"/>
## Installation

[![Maven Central](https://img.shields.io/maven-central/v/io.github.dj-raven/swing-datetime-picker?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.dj-raven/swing-datetime-picker/versions)

Add the dependency
``` xml
<dependency>
    <groupId>io.github.dj-raven</groupId>
    <artifactId>swing-datetime-picker</artifactId>
    <version>2.1.3</version>
</dependency>
```

### Snapshots
To get the latest updates before the release, you can use the snapshot version from [Sonatype Central](https://central.sonatype.com/service/rest/repository/browse/maven-snapshots/io/github/dj-raven/swing-datetime-picker/)

``` xml
<repositories>
    <repository>
        <name>Central Portal Snapshots</name>
        <id>central-portal-snapshots</id>
        <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    </repository>
</repositories>
```
Add the snapshot version
``` xml
<dependency>
    <groupId>io.github.dj-raven</groupId>
    <artifactId>swing-datetime-picker</artifactId>
    <version>2.1.3-SNAPSHOT</version>
</dependency>
```

## Usage TimePicker
| Method | Return Value | Description                                                    |
| ------------ | ------------ |----------------------------------------------------------------|
| now() | | set the time to current local time                             |
| setSelectedTime(LocalTime time) | | set the time to a specific value                               |
| clearSelectedTime() | | clear the selected time                                        |
| isTimeSelected() | `boolean` | check time is selected                                         |
| getSelectedTime() | `LocalTime` | return the selected time                                       |
| getSelectedTimeAsString() | `String` | return selected time as string                                 |
| addTimeSelectionListener(TimeSelectionListener event) | | add event time selection                                       |
| removeTimeSelectionListener(TimeSelectionListener event) | | remove event time selection                                    |
| setOrientation(int orientation) | | `SwingConstants.VERTICAL` or `SwingConstants.HORIZONTAL`       |
| setEditor(JFormattedTextField editor) | | display the selected time on the editor and allow to edit time |
| setTimeSelectionAble(TimeSelectionAble dsb) | | set time selection able                                        |
| set24HourView(boolean hour24) | | set time to 24h selection view                                 |
| is24HourView() | `boolean` | return `ture` is 24h selection view                            |
| showPopup() | | if time have editor, timepicker will show up with popup menu   |
| closePopup() | | close editor popup                                             |
| setColor(Color color) | | change base color                                              |
| setEditorIcon(Icon icon) | | change icon to editor                                          |
| setEditorValidation(boolean validation) | | validation editor                                              |
| void setValidationOnNull(boolean validationOnNull) | | validation editor on null selection                            |
| void showPopup(Component component) | | show timePicker with popup without editor                      |
| void setPopupSpace(Point popupSpace) | | set the popup space with component or editor                   |
| void toDateSelectionView() | | show date selection state                                      |

## Usage DatePicker
| Method                                                            | Return Value | Description                                                    |
|-------------------------------------------------------------------| ----------- |----------------------------------------------------------------|
| now()                                                             | | set the date to current local date                             |
| setToBack()                                                       | | slide panel to back with animation                             |
| setToForward()                                                    | | slide panel to forward with animation                          |
| selectMonth()                                                     | | show panel month slide with animation                          |
| selectYear()                                                      | | show panel year slide with animation                           |
| slideTo(LocalDate date)                                           | | slide panel to specific date                                   |
| getDateSelectionMode()                                            | `DateSelectionMode` | return the date selection mode                                 |
| setDateSelectionMode(DateSelectionMode mode)                      | | set mode `SINGLE_DATE_SELECTED` or `BETWEEN_DATE_SELECTED`     |
| setSelectedDate(LocalDate date)                                   | | set the date to a specific value                               |
| setSelectedDateRange(LocalDate from, LocalDate to)                | | set the date range to a specific value                         |
| setEditor(JFormattedTextField editor)                             | | display the selected date on the editor and allow to edit date |
| setDateSelectionAble(DateSelectionAble dsb)                       | | set date selection able                                        |
| showPopup()                                                       | | if date have editor, datepicker will show up with popup menu   |
| closePopup()                                                      | | close editor popup                                             |
| setSeparator(String separator)                                    | | set separator to between date                                  |
| setUsePanelOption(boolean usePanelOption)                         | | set datepicker use panel option                                |
| setCloseAfterSelected(boolean closeAfterSelected)                 | | if true popup will close after selected date                   |
| clearSelectedDate()                                               | | clear the selected date                                        |
| isDateSelected()                                                  | `boolean` | check date is selected                                         |
| getSelectedDate()                                                 | `LocalDate` | return the selected date                                       |
| getSelectedDateRange()                                            | `LocalDate[]` | return the selected date range                                 |
| getSelectedDateAsString()                                         | `String` | return selected date as string                                 |
| addDateSelectionListener(DateSelectionListener evt)               | | add event date selection                                       |
| removeDateSelectionListener(DateSelectionListener evt)            | | remove event date selection                                    |
| selectCurrentMonth()                                              | | select from first day to current day in current month          |
| setColor(Color color)                                             | | change base color                                              |
| setEditorIcon(Icon icon)                                          | | change icon to editor                                          |
| setDateFormat(String format)                                      | | change date format                                             |
| setEditorValidation(boolean validation)                           | | validation editor                                              |
| void setValidationOnNull(boolean validationOnNull)                | | validation editor on null selection                            |
| void setAnimationEnabled(boolean animationEnabled)                | | enable or disabled the animation                               |
| void setPanelDateOptionLabel(PanelDateOptionLabel opt)            | | set new panel date option label                                |
| void setStartWeekOnMonday(boolean startWeekOnMonday)              | | show the monday is the start of the week                       |
| void showPopup(Component component)                               | | show datePicker with popup without editor                      |
| void setPopupSpace(Point popupSpace)                              | | set the popup space with component or editor                   |
| void setHourSelectionView(boolean hourSelectionView)              | | show hour or minute selection state                            |
| void setSelectionArc(float selectionArc)                          | | set date selection border arc                                  |
| void setDefaultDateCellRenderer(DefaultDateCellRenderer renderer) | | set date cell renderer to paint custom graphics                |
| public static void setDefaultWeekdays(String[] defaultWeekdays)   | | `static` method for set default label week days                |
| public static void setDefaultMonths(String[] defaultMonths)       | | `static` method for set default label months                   |
| public static String[] getDefaultWeekdays()                       | | `static` method for get default label week days                |
| public static String[] getDefaultMonths()                         | | `static` method for get default label months                   |

## Library Resources
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) - FlatLaf library for the modern UI design theme
- [MigLayout](https://github.com/mikaelgrev/miglayout) - MigLayout library for flexible layout management
