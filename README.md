# Swing Datetime Picker
A simple datetime picker implementation using Java Swing, built with the `flatlaf` UI library and `miglayout` for layout management.

This project provides a datetime picker component that can be easily integrated into Java Swing applications. It leverages flatlaf for a modern look and miglayout for flexible and easy-to-use layout management.

<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/timepicker-dark.png" alt="timepicker dark" width="300"/>&nbsp;
<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/timepicker-light.png" alt="timepicker light" width="300"/>

## Installation
This project library do not available in maven central. so you can install with the jar library
- Copy jar library file to the root project. exp : `library/swing-datetime-picker-1.0.0.jar`
- Add this code to `pom.xml`
``` xml
<dependency>
    <groupId>raven.datetime</groupId>
    <artifactId>swing-datetime-picker</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <systemPath>${basedir}/library/swing-datetime-picker-1.0.0.jar</systemPath>
</dependency>
```
- Other library are use with this library
``` xml
<dependency>
  <groupId>com.formdev</groupId>
  <artifactId>flatlaf</artifactId>
  <version>3.2.5</version>
</dependency>

<dependency>
  <groupId>com.formdev</groupId>
  <artifactId>flatlaf-extras</artifactId>
  <version>3.2.5</version>
</dependency>

<dependency>
    <groupId>com.miglayout</groupId>
    <artifactId>miglayout-swing</artifactId>
    <version>11.3</version>
</dependency>
```
## Usage TimePicker
| Method | Return Value | Description |
| ------------ | ------------ | ------------ |
| now() | `void` | set the time to current local time |
| setSelectedTime(LocalTime time) | `void` | set the time to a specific value |
| clearSelectedTime() | `void` | clear the selected time |
| isTimeSelected() | `boolean` | check time is selected |
| getSelectedTime() | `LocalTime` | get the selected time |
| getSelectedTimeAsString() | `String` | get selected time as string |
| addTimeSelectionListener(TimeSelectionListener event) | `void` | add event time selection |
| removeTimeSelectionListener(TimeSelectionListener event) | `void` | remove event time selection |
| removeAllTimeSelectionListener() | `void` | remove all event tiem selection |
| setOrientation(int orientation) | `void` | `SwingConstants.VERTICAL` or `SwingConstants.HORIZONTAL` |
| setEditor(JFormattedTextField editor) | `void` | disply the selected time on the editor and allow to edit time |
| set24HourView(boolean hour24) | `void` | set time to 24h selection view |
| is24HourView() | `boolean` | return `ture` is 24h selection view |
| showPopup() | `void` | if time have editor, timepicker will show up with popup menu |


## Usage DatePicker
- Next Coming

## Library Resources
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) - FlatLaf library for the modern UI design theme
- [MigLayout](https://github.com/mikaelgrev/miglayout) - MigLayout library for flexible layout management

