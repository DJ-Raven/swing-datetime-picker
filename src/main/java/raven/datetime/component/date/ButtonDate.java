package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatUIUtils;
import raven.datetime.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonDate extends JButton {

    private final DatePicker datePicker;
    private final SingleDate date;
    private final int rowIndex;
    private boolean press;
    private boolean hover;

    public ButtonDate(DatePicker datePicker, SingleDate date, boolean enable, int rowIndex) {
        this.datePicker = datePicker;
        this.date = date;
        this.rowIndex = rowIndex;
        setText(date.getDay() + "");
        init(enable);
    }

    private void init(boolean enable) {
        setContentAreaFilled(false);
        addActionListener(e -> {
            if (datePicker.isEnabled()) {
                datePicker.getDateSelectionModel().selectDate(date);
                hover = false;
                PanelDate panelDate = (PanelDate) getParent();
                panelDate.checkSelection();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (datePicker.isEnabled() && isEnabled() && datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
                    if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                        datePicker.closePopup();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (datePicker.isEnabled() && SwingUtilities.isLeftMouseButton(e)) {
                    press = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (datePicker.isEnabled() && SwingUtilities.isLeftMouseButton(e)) {
                    press = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (datePicker.isEnabled()) {
                    hover = true;
                    if (datePicker.getDateSelectionModel().getDateSelectionMode() == DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED && datePicker.getDateSelectionModel().getDate() != null) {
                        datePicker.getDateSelectionModel().setHoverDate(date);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (datePicker.isEnabled()) {
                    hover = false;
                }
            }
        });
        if (enable) {
            putClientProperty(FlatClientProperties.STYLE, "" +
                    "margin:7,7,7,7;" +
                    "focusWidth:2;" +
                    "selectedForeground:contrast($Component.accentColor,$Button.background,#fff)");
        } else {
            putClientProperty(FlatClientProperties.STYLE, "" +
                    "margin:7,7,7,7;" +
                    "focusWidth:2;" +
                    "selectedForeground:contrast($Component.accentColor,$Button.background,#fff);" +
                    "foreground:$Button.disabledText");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        DefaultDateCellRenderer dateCellRenderer = datePicker.getDefaultDateCellRenderer();
        if (dateCellRenderer != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                FlatUIUtils.setRenderingHints(g2);
                dateCellRenderer.paint(g2, datePicker, this, date, getWidth(), getHeight());
            } finally {
                g2.dispose();
            }
        }
        super.paintComponent(g);
    }

    public boolean isDateSelected() {
        DateSelectionModel dateSelectionModel = datePicker.getDateSelectionModel();
        if (dateSelectionModel.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED) {
            return date.same(dateSelectionModel.getDate());
        } else {
            return date.same(dateSelectionModel.getDate()) || date.same(dateSelectionModel.getToDate());
        }
    }

    public SingleDate getDate() {
        return date;
    }

    public boolean isPress() {
        return press;
    }

    public boolean isHover() {
        return hover;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}
