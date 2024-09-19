# Swing Datetime Picker
A simple datetime picker implementation using Java Swing, built with the `flatlaf` UI library and `miglayout` for layout management.

This project provides a datetime picker component that can be easily integrated into Java Swing applications. It leverages flatlaf for a modern look and miglayout for flexible and easy-to-use layout management.

<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/timepicker-dark.png" alt="timepicker dark" width="300"/>&nbsp;
<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/timepicker-light.png" alt="timepicker light" width="300"/>
<br/>
<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/datepicker-dark.png" alt="datepicker dark" width="300"/>&nbsp;
<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/datepicker-light.png" alt="datepicker light" width="300"/>
## Installation

[![Maven Central](https://img.shields.io/maven-central/v/io.github.dj-raven/swing-datetime-picker?label=Maven%20Central)](https://search.maven.org/artifact/io.github.dj-raven/swing-datetime-picker)

Add the dependency
``` xml
<dependency>
    <groupId>io.github.dj-raven</groupId>
    <artifactId>swing-datetime-picker</artifactId>
    <version>1.4.0</version>
</dependency>
```
## Usage TimePicker
| Method | Return Value | Description |
| ------------ | ------------ | ------------ |
| now() | | set the time to current local time |
| setSelectedTime(LocalTime time) | | set the time to a specific value |
| clearSelectedTime() | | clear the selected time |
| isTimeSelected() | `boolean` | check time is selected |
| getSelectedTime() | `LocalTime` | return the selected time |
| getSelectedTimeAsString() | `String` | return selected time as string |
| addTimeSelectionListener(TimeSelectionListener event) | | add event time selection |
| removeTimeSelectionListener(TimeSelectionListener event) | | remove event time selection |
| removeAllTimeSelectionListener() | | remove all event time selection |
| setOrientation(int orientation) | | `SwingConstants.VERTICAL` or `SwingConstants.HORIZONTAL` |
| setEditor(JFormattedTextField editor) | | disply the selected time on the editor and allow to edit time |
| set24HourView(boolean hour24) | | set time to 24h selection view |
| is24HourView() | `boolean` | return `ture` is 24h selection view |
| showPopup() | | if time have editor, timepicker will show up with popup menu |
| closePopup() | | close editor popup |
| setColor(Color color) | | change base color |
| setEditorIcon(Icon icon) | | change icon to editor |

## Usage DatePicker
| Method | Return Value | Description |
| ------------ | ------------ | ------------ |
| now() | | set the date to current local date |
| setToBack() | | slide panel to back with animation |
| setToForward() | | slide panel to forward with animation |
| selectMonth() | | show panel month slide with animation |
| selectYear() | | show panel year slide with animation |
| slideTo(LocalDate date) | | slide panel to specific date |
| getDateSelectionMode() | `DateSelectionMode` | return the date selection mode |
| setDateSelectionMode(DateSelectionMode mode) | | set mode `SINGLE_DATE_SELECTED` or `BETWEEN_DATE_SELECTED` |
| setSelectedDate(LocalDate date) | | set the date to a specific value |
| setSelectedDateRange(LocalDate from, LocalDate to) | | set the date range to a specific value |
| setEditor(JFormattedTextField editor) | | disply the selected date on the editor and allow to edit date |
| setDateSelectionAble(DateSelectionAble dsb) | | set date selectionable |
| showPopup() | | if date have editor, datepicker will show up with popup menu |
| closePopup() | | close editor popup |
| setSeparator(String separator) | | set separator to between date |
| setUsePanelOption(boolean usePanelOption) | | set datepicker use panel option |
| setCloseAfterSelected(boolean closeAfterSelected) | | if true popup will close after selected date |
| clearSelectedDate() | | clear the selected date |
| isDateSelected() | `boolean` | check date is selected |
| getSelectedDate() | `LocalDate` | return the selected date |
| getSelectedDateRange() | `LocalDate[]` | return the selected date range |
| getSelectedDateAsString() | `String` | return selected date as string |
| addDateSelectionListener(DateSelectionListener event) | | add event date selection |
| removeDateSelectionListener(DateSelectionListener event) | | remove event date selection |
| removeAllDateSelectionListener() | | remove all event date selection |
| selectCurrentMonth() | | select from first day to current day in current month |
| setColor(Color color) | | change base color |
| setEditorIcon(Icon icon) | | change icon to editor |
| setDateFormat(String format) | | change date format |
| setEditorValidation(boolean validation) | | validation editor |
| void setValidationOnNull(boolean validationOnNull) | | validation editor on null selection |

## Library Resources
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) - FlatLaf library for the modern UI design theme
- [MigLayout](https://github.com/mikaelgrev/miglayout) - MigLayout library for flexible layout management
