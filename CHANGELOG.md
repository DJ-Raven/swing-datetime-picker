## 1.3.0

#### New features and improvements

- DatePicker:
  - Add new method `setDateFormat(String format)` to format date (issues #5)

#### Fixed bugs

- PanelSlider `flush` image after end of animation
- Fixed editor popup error when using JDialog

## 1.2.0

#### New features and improvements

- DatePicker:
  - Add new method `selectCurrentMonth()` (PR #2)
- TimePicker:
  - Add new method `setColor(Color color)` (issues #3)
- Popup menu will show inside the windows frame
- Add new method `setEditorIcon(Icon icon)`

#### Fixed bugs

- DatePicker:
  - Invert between date selected (when `date` is after `toDate`) (PR #2)
  - Fixed `between date` resend value when editor value changed
- TimePicker: 
  - Fixed event time changed not work while `am` or `pm` changed in editor
  - Fixed time format by use `Locale.ENGLISH` (issues #4)
- Fixed reset old editor to default after changed new editor
- Fixed event `SelectionListener` invoke only value changed

## 1.1.0

#### New features and improvements

- Add new datepicker (PR #1) 
- Update style background in timepicker

## 1.0.0

- Initial release
