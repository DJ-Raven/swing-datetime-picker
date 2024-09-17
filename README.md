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
| now() | `void` | set the time to current local time |
| setSelectedTime(LocalTime time) | `void` | set the time to a specific value |
| clearSelectedTime() | `void` | clear the selected time |
| isTimeSelected() | `boolean` | check time is selected |
| getSelectedTime() | `LocalTime` | return the selected time |
| getSelectedTimeAsString() | `String` | return selected time as string |
| addTimeSelectionListener(TimeSelectionListener event) | `void` | add event time selection |
| removeTimeSelectionListener(TimeSelectionListener event) | `void` | remove event time selection |
| removeAllTimeSelectionListener() | `void` | remove all event tiem selection |
| setOrientation(int orientation) | `void` | `SwingConstants.VERTICAL` or `SwingConstants.HORIZONTAL` |
| setEditor(JFormattedTextField editor) | `void` | disply the selected time on the editor and allow to edit time |
| set24HourView(boolean hour24) | `void` | set time to 24h selection view |
| is24HourView() | `boolean` | return `ture` is 24h selection view |
| showPopup() | `void` | if time have editor, timepicker will show up with popup menu |
| closePopup() | `void` | close editor popup |
| setColor(Color color) | `void` | change base color |
| setEditorIcon(Icon icon) | `void` | change icon to editor |


## Usage DatePicker
| Method | Return Value | Description |
| ------------ | ------------ | ------------ |
| now() | `void` | set the date to current local date |
| setToBack() | `void` | slide panel to back with animation |
| setToForward() | `void` | slide panel to forward with animation |
| selectMonth() | `void` | show panel month slide with animation |
| selectYear() | `void` | show panel year slide with animation |
| slideTo(LocalDate date) | `void` | slide panel to specific date |
| getDateSelectionMode() | `DateSelectionMode` | return the date selectionmode |
| setDateSelectionMode(DateSelectionMode mode) | `void` | set mode `SINGLE_DATE_SELECTED` or `BETWEEN_DATE_SELECTED` |
| setSelectedDate(LocalDate date) | `void` | set the date to a specific value |
| setSelectedDateRange(LocalDate from, LocalDate to) | `void` | set the date range to a specific value |
| setEditor(JFormattedTextField editor) | `void` | disply the selected date on the editor and allow to edit date |
| setDateSelectionAble(DateSelectionAble dsb) | `void` | set date selectionable |
| showPopup() | `void` | if date have editor, datepicker will show up with popup menu |
| closePopup() | `void` | close editor popup |
| setSeparator(String separator) | `void` | set separator to between date |
| setUsePanelOption(boolean usePanelOption) | `void` | set datepicker use panel option |
| setCloseAfterSelected(boolean closeAfterSelected) | `void` | if true popup will close after selected date |
| clearSelectedDate() | `void` | clear the selected date |
| isDateSelected() | `boolean` | check date is selected |
| getSelectedDate() | `LocalDate` | return the selected date |
| getSelectedDateRange() | `LocalDate[]` | return the selected date range |
| getSelectedDateAsString() | `String` | return selected date as string |
| addDateSelectionListener(DateSelectionListener event) | `void` | add event date selection |
| removeDateSelectionListener(DateSelectionListener event) | `void` | remove event date selection |
| removeAllDateSelectionListener() | `void` | remove all event date selection |
| selectCurrentMonth() | `void` | select from first day to current day in current month |
| setEditorIcon(Icon icon) | `void` | change icon to editor |


## Library Resources
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) - FlatLaf library for the modern UI design theme
- [MigLayout](https://github.com/mikaelgrev/miglayout) - MigLayout library for flexible layout management

