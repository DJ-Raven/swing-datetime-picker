# Changelog

## [2.1.3] - 2025-07-31

### New features and improvements

- DatePicker:
    - Add global `DatePicker.defaultMonths` for change the default label months (issue #24) and (PR #23)

### Changed

- Changed new datepicker and timepicker editor icon

### Fixed bugs

- DatePicker:
    - Fixed click option in PanelDateOption not working for single date when use date between mode (PR #25)

## [2.1.2] - 2025-06-08

### New features and improvements

- DatePicker:
    - Add new option `defaultDateCellRenderer` to custom date cell renderer (issue #18) and (PR #19)
    - Add global `DatePicker.defaultWeekdays` for change the default label weekdays (issue #20)
- Add new method `getEditor()` return as `JFormattedTextField` is editor has set

### Changed

- TimePicker:
    - Time header button AM and PM now use `Locale.ENGLISH` as string symbols

### Fixed bugs

- DatePicker:
    - Fixed incorrect start day of week when `startWeekOnMonday` is true (issue #21)
- TimePicker:
    - Fixed incorrect selection location in 24 hour view.

## [2.1.1] - 2025-05-05

### Changed

- DatePicker:
    - Removed component fixed value size. (previous width:260, height:250)
    - Add new option `selectionArc` default `999f`

## [2.1.0] - 2025-04-25

### New features and improvements

- DatePicker:
    - Add new option `startWeekOnMonday` for show the monday is the start of the week (default `false`)
    - Add new method `toDateSelectionView()` for show date selection state (show as current selected date. if selected
      date `null` show as system current date) and this method call by default when show with popup
- TimePicker:
    - Add new method `setHourSelectionView(boolean hourSelectionView)` if `true` show hour selection state. if `false`
      show minute selection state. and this method call by default when show with popup
- Date and Time Popup:
    - Add new method:
        - `showPopup(Component component)` to show datepicker or timePicker with popup without editor
        - `setPopupSpace(Point popupSpace)` to set the popup space with component or editor

### Changed

- Add popup frame insets default `(5,5,5,5)`
- FlatLaf: update to `v3.6`
- Test: removed `flatlaf-fonts-roboto` and style properties in resources

## [2.0.0] - 2024-12-24

### New features and improvements

- DatePicker:
    - When disabled, users can no longer change the date selection
- TimePicker:
    - When disabled, users can no longer change the time selection
    - Add time selection able and editor input validation check (issue #14) and (PR #15)

### Changed

- Rewrote and refactored existing code for improved readability and performance
- Move `raven.datetime.component.date.DatePicker` to `raven.datetime.DatePicker`
- Move `raven.datetime.component.time.TimePicker` to `raven.datetime.TimePicker`

## [1.4.1] - 2024-11-29

### New features and improvements

- DatePicker:
    - Add new option `animationEnabled`
    - Date popup in `SINGLE_DATE_SELECTED` mode, now auto close when double click
    - Add `PanelDateOptionLabel` to custom panel date option (PR #10)

### Changed

- FlatLaf: update to v3.5.2

### Fixed bugs

- DatePicker:
    - Fixed editor input validation
    - Miglayout use `novisualpadding` and set slider fix size as `260,250`
- TimePicker:
    - Fixed `editor` not changed value when switch between `24h` and `12h` view

## [1.4.0] - 2024-09-14

### New features and improvements

- DatePicker:
    - Add editor input validation (PR #9)

### Changed

- Miglayout: back to v5.3 to support java 8

## [1.3.0] - 2024-07-05

### New features and improvements

- DatePicker:
    - Add new method:
        - `setDateFormat(String format)` to format date (issues #5)
        - `setColor(Color color)` (issues #6)

### Fixed bugs

- PanelSlider `flush` image after end of animation
- Fixed editor popup error when using JDialog

## [1.2.0] - 2024-05-06

### New features and improvements

- DatePicker:
    - Add new method `selectCurrentMonth()` (PR #2)
- TimePicker:
    - Add new method `setColor(Color color)` (issues #3)
- Popup menu will show inside the windows frame
- Add new method `setEditorIcon(Icon icon)`

### Fixed bugs

- DatePicker:
    - Invert between date selected (when `date` is after `toDate`) (PR #2)
    - Fixed `between date` resend value when editor value changed
- TimePicker:
    - Fixed event time changed not work while `am` or `pm` changed in editor
    - Fixed time format by use `Locale.ENGLISH` (issues #4)
- Fixed reset old editor to default after changed new editor
- Fixed event `SelectionListener` invoke only value changed

## [1.1.0] - 2024-04-13

### New features and improvements

- Add new datepicker (PR #1)
- Update style background in timepicker

## [1.0.0] - 2023-12-17

- Initial release
