# Swing Datetime Picker
A simple datetime picker implementation using Java Swing, built with the `flatlaf` UI library and `miglayout` for layout management.

This project provides a datetime picker component that can be easily integrated into Java Swing applications. It leverages flatlaf for a modern look and miglayout for flexible and easy-to-use layout management.

<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/timepicker-dark.png" alt="timepicker dark" width="300"/>&nbsp;
<img src="https://github.com/DJ-Raven/swing-datetime-picker/blob/main/screenshot/timepicker-light.png" alt="timepicker light" width="300"/>

## Installation
This project library do not available in maven central. so you can install with the jar library
- Copy jar library file to the root project. ex : `library/swing-datetime-picker-1.0.0.jar`
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

## Usage DatePicker
- Next Coming

## Library Resources
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) - FlatLaf library for the modern UI design theme
- [MigLayout](https://github.com/mikaelgrev/miglayout) - MigLayout library for flexible layout management

