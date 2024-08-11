package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.datetime.util.InputUtils;
import raven.datetime.util.InputValidationListener;
import raven.datetime.util.Utils;
import raven.swing.slider.PanelSlider;
import raven.swing.slider.SimpleTransition;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatePicker extends JPanel {

    private DateTimeFormatter format;
    private String dateFormatPattern;
    private final List<DateSelectionListener> events = new ArrayList<>();
    private DateSelectionListener dateSelectionListener;
    private InputValidationListener inputValidationListener;
    private final DateSelection dateSelection = new DateSelection(this);
    private PanelMonth.EventMonthChanged eventMonthChanged;
    private PanelYear.EventYearChanged eventYearChanged;
    private PanelDateOption panelDateOption;
    private InputUtils.ValueCallback valueCallback;
    private JFormattedTextField editor;
    private Icon editorIcon;
    private JPopupMenu popupMenu;
    private String separator = " to ";
    private boolean usePanelOption;
    private boolean closeAfterSelected;
    private int month = 10;
    private int year = 2023;
    private Color color;
    private LookAndFeel oldThemes = UIManager.getLookAndFeel();
    private JButton editorButton;
    private LocalDate oldSelectedDate;
    private LocalDate oldSelectedToDate;
    private boolean editorValidation = true;
    private boolean isValid;
    private boolean validationOnNull;
    private String defaultPlaceholder;

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
        putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken($Panel.background,2%);" +
                "[dark]background:lighten($Panel.background,2%);");
        setLayout(new MigLayout("wrap,insets 10,fill", "[fill]"));
        dateFormatPattern = "dd/MM/yyyy";
        format = DateTimeFormatter.ofPattern(dateFormatPattern);
        panelSlider = new PanelSlider();
        header = new Header(getEventHeader());
        eventMonthChanged = createEventMonthChanged();
        eventYearChanged = createEventYearChanged();
        add(header);
        add(panelSlider, "width 260,height 250");
        initDate();
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

    private PanelYear.EventYearChanged createEventYearChanged() {
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
        if (events == null || events.isEmpty()) {
            return;
        }
        LocalDate date;
        LocalDate toDate = null;
        if (dateSelection.dateSelectionMode == DateSelectionMode.SINGLE_DATE_SELECTED) {
            date = getSelectedDate();
        } else {
            if (!isDateSelected()) {
                oldSelectedToDate = null;
                return;
            }
            LocalDate dates[] = getSelectedDateRange();
            date = dates[0];
            toDate = dates[1];
        }
        boolean date1 = checkDate(date, oldSelectedDate);
        boolean date2 = checkDate(toDate, oldSelectedToDate);
        if (dateSelection.dateSelectionMode == DateSelectionMode.SINGLE_DATE_SELECTED && !date1) {
            return;
        } else if (!(date1 || date2)) {
            return;
        }
        oldSelectedDate = date;
        oldSelectedToDate = toDate;
        EventQueue.invokeLater(() -> {
            for (DateSelectionListener event : events) {
                event.dateSelected(new DateEvent(this));
            }
            if (panelDateOption != null) {
                panelDateOption.setSelectedCustom();
            }
        });
    }

    private boolean checkDate(LocalDate date, LocalDate date1) {
        if ((date == null && date1 == null)) {
            return false;
        } else if (date != null && date1 != null) {
            if (date.compareTo(date1) == 0) {
                return false;
            }
        }
        return true;
    }

    private Header header;
    private PanelSlider panelSlider;

    public DateSelectionMode getDateSelectionMode() {
        return dateSelection.dateSelectionMode;
    }

    public void setDateSelectionMode(DateSelectionMode dateSelectionMode) {
        if (this.dateSelection.dateSelectionMode != dateSelectionMode) {
            this.dateSelection.dateSelectionMode = dateSelectionMode;
            if (editor != null) {
                InputUtils.changeDateFormatted(editor, dateFormatPattern, dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED, separator, getInputValidationListener());
                this.defaultPlaceholder = null;
                clearSelectedDate();
                commitEdit();
            }
            repaint();
        }
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

    public void selectCurrentMonth() {
        LocalDate date = LocalDate.now();
        if (dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED) {
            setSelectedDateRange(date.withDayOfMonth(1), date);
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

    public void setEditor(JFormattedTextField editor) {
        if (editor != this.editor) {
            if (this.editor != null) {
                uninstallEditor(this.editor);
            }
            if (editor != null) {
                installEditor(editor);
            }
            this.editor = editor;
            if (editor != null) {
                if (editorValidation) {
                    validChanged(editor, isValid);
                } else {
                    validChanged(editor, false);
                }
            }
        }
    }

    public Icon getEditorIcon() {
        return editorIcon;
    }

    public void setEditorIcon(Icon editorIcon) {
        this.editorIcon = editorIcon;
        if (editorButton != null) {
            editorButton.setIcon(editorIcon);
        }
    }

    public DateSelectionAble getDateSelectionAble() {
        return dateSelection.getDateSelectionAble();
    }

    public void setDateSelectionAble(DateSelectionAble dateSelectionAble) {
        this.dateSelection.setDateSelectionAble(dateSelectionAble);
        Component com = panelSlider.getSlideComponent();
        if (com instanceof PanelDate) {
            ((PanelDate) com).load();
        }
    }

    public void showPopup() {
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
            popupMenu.putClientProperty(FlatClientProperties.STYLE, "" +
                    "borderInsets:1,1,1,1");
            popupMenu.add(this);
        }
        if (UIManager.getLookAndFeel() != oldThemes) {
            // Component in popup not update UI when change themes, so need to update when popup show
            SwingUtilities.updateComponentTreeUI(popupMenu);
            oldThemes = UIManager.getLookAndFeel();
        }
        Point point = Utils.adjustPopupLocation(popupMenu, editor);
        popupMenu.show(editor, point.x, point.y);
    }

    public void closePopup() {
        if (popupMenu != null) {
            popupMenu.setVisible(false);
            repaint();
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    public void setSeparator(String separator) {
        if (separator == null) {
            throw new IllegalArgumentException("separator can't be null");
        }
        if (!this.separator.equals(separator)) {
            this.separator = separator;
            if (editor != null) {
                InputUtils.changeDateFormatted(editor, dateFormatPattern, dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED, separator, getInputValidationListener());
                this.defaultPlaceholder = null;
                runEventDateChanged();
            }
        }
    }

    public void setDateFormat(String format) {
        this.format = DateTimeFormatter.ofPattern(format);
        if (editor != null) {
            InputUtils.changeDateFormatted(editor, format, dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED, separator, getInputValidationListener());
            this.defaultPlaceholder = null;
        }
        this.dateFormatPattern = format;
    }

    public boolean isEditorValidation() {
        return editorValidation;
    }

    public void setEditorValidation(boolean editorValidation) {
        if (this.editorValidation != editorValidation) {
            this.editorValidation = editorValidation;
            if (editor != null) {
                if (editorValidation) {
                    validChanged(editor, isValid);
                } else {
                    validChanged(editor, true);
                }
            }
        }
    }

    public boolean isValidationOnNull() {
        return validationOnNull;
    }

    public void setValidationOnNull(boolean validationOnNull) {
        if (this.validationOnNull != validationOnNull) {
            this.validationOnNull = validationOnNull;
            commitEdit();
        }
    }

    public String getDateFormat() {
        return this.dateFormatPattern;
    }

    public boolean isUsePanelOption() {
        return usePanelOption;
    }

    public void setUsePanelOption(boolean usePanelOption) {
        if (this.usePanelOption != usePanelOption) {
            this.usePanelOption = usePanelOption;
            if (usePanelOption) {
                if (panelDateOption == null) {
                    panelDateOption = new PanelDateOption(this);
                }
                add(panelDateOption, "dock east,gap 0 10 10 10");
                repaint();
                revalidate();
            } else {
                if (panelDateOption != null) {
                    remove(panelDateOption);
                    panelDateOption = null;
                    repaint();
                    revalidate();
                }
            }
        }
    }

    public boolean isCloseAfterSelected() {
        return closeAfterSelected;
    }

    public void setCloseAfterSelected(boolean closeAfterSelected) {
        this.closeAfterSelected = closeAfterSelected;
    }

    public String getSeparator() {
        return separator;
    }

    public void clearSelectedDate() {
        dateSelection.setSelectDate(null, null);
        updateSelected();
        panelSlider.repaint();
    }

    public boolean isDateSelected() {
        if (dateSelection.dateSelectionMode == DateSelectionMode.SINGLE_DATE_SELECTED) {
            return dateSelection.getDate() != null;
        } else {
            return dateSelection.getDate() != null && dateSelection.getToDate() != null;
        }
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

    public String getSelectedDateAsString() {
        if (isDateSelected()) {
            if (dateSelection.dateSelectionMode == DateSelectionMode.SINGLE_DATE_SELECTED) {
                return format.format(getSelectedDate());
            } else {
                LocalDate[] dates = getSelectedDateRange();
                return format.format(dates[0]) + separator + format.format(dates[1]);
            }
        } else {
            return null;
        }
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
        updateSelected();
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

    private void updateSelected() {
        Component com = panelSlider.getSlideComponent();
        if (com instanceof PanelDate) {
            ((PanelDate) com).checkSelection();
        } else if (com instanceof PanelMonth) {
            ((PanelMonth) com).checkSelection();
        } else if (com instanceof PanelYear) {
            ((PanelYear) com).checkSelection();
        }
    }

    private void installEditor(JFormattedTextField editor) {
        JToolBar toolBar = new JToolBar();
        editorButton = new JButton(editorIcon != null ? editorIcon : new FlatSVGIcon("raven/datetime/icon/calendar.svg", 0.8f));
        toolBar.add(editorButton);
        editorButton.addActionListener(e -> {
            showPopup();
        });
        InputUtils.useDateInput(editor, dateFormatPattern, dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED, separator, getValueCallback(), getInputValidationListener());
        editor.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, toolBar);
        addDateSelectionListener(getDateSelectionListener());
    }

    private void uninstallEditor(JFormattedTextField editor) {
        if (editor != null) {
            editorButton = null;
            InputUtils.removePropertyChange(editor);
            if (dateSelectionListener != null) {
                removeDateSelectionListener(dateSelectionListener);
            }
        }
    }

    private InputUtils.ValueCallback getValueCallback() {
        if (valueCallback == null) {
            valueCallback = value -> {
                if (value == null && isDateSelected()) {
                    clearSelectedDate();
                } else {
                    if (value != null && !value.equals(getSelectedDateAsString())) {
                        if (dateSelection.dateSelectionMode == DateSelectionMode.SINGLE_DATE_SELECTED) {
                            LocalDate date = InputUtils.stringToDate(format, value.toString());
                            if (date != null) {
                                setSelectedDate(date);
                            }
                        } else {
                            LocalDate[] dates = InputUtils.stringToDate(format, separator, value.toString());
                            if (dates != null) {
                                setSelectedDateRange(dates[0], dates[1]);
                            }
                        }
                    }
                }
            };
        }
        return valueCallback;
    }

    private DateSelectionListener getDateSelectionListener() {
        if (dateSelectionListener == null) {
            dateSelectionListener = new DateSelectionListener() {
                @Override
                public void dateSelected(DateEvent dateEvent) {
                    if (isDateSelected()) {
                        String value;
                        if (dateSelection.dateSelectionMode == DateSelectionMode.SINGLE_DATE_SELECTED) {
                            value = format.format(getSelectedDate());
                        } else {
                            LocalDate dates[] = getSelectedDateRange();
                            value = format.format(dates[0]) + separator + format.format(dates[1]);
                        }
                        if (!editor.getText().toLowerCase().equals(value.toLowerCase())) {
                            editor.setValue(value);
                        }
                    } else {
                        editor.setValue(null);
                    }
                }
            };
        }
        return dateSelectionListener;
    }

    private InputValidationListener getInputValidationListener() {
        if (inputValidationListener == null) {
            inputValidationListener = new InputValidationListener() {

                @Override
                public boolean isValidation() {
                    return dateSelection.getDateSelectionAble() != null;
                }

                @Override
                public void inputChanged(boolean status) {
                    checkValidation(status);
                }

                @Override
                public boolean checkDateSelectionAble(LocalDate date) {
                    if (dateSelection.getDateSelectionAble() == null) return true;
                    return dateSelection.getDateSelectionAble().isDateSelectedAble(date);
                }
            };
        }
        return inputValidationListener;
    }

    private void checkValidation(boolean status) {
        boolean valid = status || isEditorValidationOnNull();
        if (isValid != valid) {
            isValid = valid;
            if (editor != null) {
                if (editorValidation) {
                    validChanged(editor, valid);
                }
            }
        }
    }

    protected void validChanged(JFormattedTextField editor, boolean isValid) {
        String style = isValid ? null : FlatClientProperties.OUTLINE_ERROR;
        editor.putClientProperty(FlatClientProperties.OUTLINE, style);
    }

    private boolean isEditorValidationOnNull() {
        if (validationOnNull) {
            return false;
        }
        return editor != null && editor.getText().equals(getDefaultPlaceholder());
    }

    private String getDefaultPlaceholder() {
        if (defaultPlaceholder == null) {
            if (dateSelection.dateSelectionMode == DateSelectionMode.BETWEEN_DATE_SELECTED) {
                String d = InputUtils.datePatternToInputFormat(dateFormatPattern, "-");
                defaultPlaceholder = d + separator + d;
            } else {
                defaultPlaceholder = InputUtils.datePatternToInputFormat(dateFormatPattern, "-");
            }
        }
        return defaultPlaceholder;
    }

    private void commitEdit() {
        if (editor != null && editorValidation) {
            try {
                editor.commitEdit();
            } catch (ParseException e) {
            }
        }
    }

    public enum DateSelectionMode {
        SINGLE_DATE_SELECTED, BETWEEN_DATE_SELECTED
    }
}
