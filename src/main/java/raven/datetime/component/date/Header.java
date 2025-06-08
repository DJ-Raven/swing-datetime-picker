package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.event.DateControlEvent;
import raven.datetime.component.date.event.DateControlListener;

import javax.swing.*;
import java.text.DateFormatSymbols;

public class Header extends JPanel {

    protected JButton buttonMonth;
    protected JButton buttonYear;

    protected Icon backIcon;

    protected Icon forwardIcon;

    public Header() {
        this(10, 2023);
    }

    public Header(int month, int year) {
        init(month, year);
    }

    private void init(int month, int year) {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        setLayout(new MigLayout("fill,insets 3", "[]push[][]push[]", "fill"));

        JButton cmdBack = createButton();
        JButton cmdNext = createButton();

        backIcon = createDefaultBackIcon();
        forwardIcon = createDefaultForwardIcon();
        cmdBack.setIcon(backIcon);
        cmdNext.setIcon(forwardIcon);

        buttonMonth = createButton();
        buttonYear = createButton();

        cmdBack.addActionListener(e -> fireDateControlChanged(new DateControlEvent(this, DateControlEvent.DAY_STATE, DateControlEvent.BACK)));
        cmdNext.addActionListener(e -> fireDateControlChanged(new DateControlEvent(this, DateControlEvent.DAY_STATE, DateControlEvent.FORWARD)));
        buttonMonth.addActionListener(e -> fireDateControlChanged(new DateControlEvent(this, DateControlEvent.DAY_STATE, DateControlEvent.MONTH)));
        buttonYear.addActionListener(e -> fireDateControlChanged(new DateControlEvent(this, DateControlEvent.DAY_STATE, DateControlEvent.YEAR)));

        add(cmdBack);
        add(buttonMonth);
        add(buttonYear);
        add(cmdNext);
        setDate(month, year);
    }

    protected JButton createButton() {
        JButton button = new JButton();
        button.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;" +
                "arc:10;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "margin:0,5,0,5");
        return button;
    }

    protected Icon createDefaultBackIcon() {
        return new FlatSVGIcon("raven/datetime/icon/back.svg");
    }

    protected Icon createDefaultForwardIcon() {
        return new FlatSVGIcon("raven/datetime/icon/forward.svg");
    }

    public void addDateControlListener(DateControlListener listener) {
        listenerList.add(DateControlListener.class, listener);
    }

    public void removeDateControlListener(DateControlListener listener) {
        listenerList.remove(DateControlListener.class, listener);
    }

    public void fireDateControlChanged(DateControlEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DateControlListener.class) {
                ((DateControlListener) listeners[i + 1]).dateControlChanged(event);
            }
        }
    }

    public void setDate(int month, int year) {
        buttonMonth.setText(DateFormatSymbols.getInstance().getMonths()[month]);
        buttonYear.setText(year + "");
    }

    public Icon getBackIcon() {
        return backIcon;
    }

    public void setBackIcon(Icon backIcon) {
        this.backIcon = backIcon;
    }

    public Icon getForwardIcon() {
        return forwardIcon;
    }

    public void setForwardIcon(Icon forwardIcon) {
        this.forwardIcon = forwardIcon;
    }
}
