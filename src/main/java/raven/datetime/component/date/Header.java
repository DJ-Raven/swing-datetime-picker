package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.text.DateFormatSymbols;

public class Header extends JPanel {

    private final EventHeaderChanged headerChanged;

    public void setDate(int month, int year) {
        buttonMonth.setText(DateFormatSymbols.getInstance().getMonths()[month]);
        buttonYear.setText(year + "");
    }

    public Header(EventHeaderChanged headerChanged) {
        this.headerChanged = headerChanged;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        setLayout(new MigLayout("fill,insets 3", "[]push[][]push[]", "fill"));

        JButton cmdBack = createButton();
        JButton cmdNext = createButton();
        cmdBack.setIcon(createIcon("back.svg"));
        cmdNext.setIcon(createIcon("forward.svg"));
        buttonMonth = createButton();
        buttonYear = createButton();

        cmdBack.addActionListener(e -> headerChanged.back());
        cmdNext.addActionListener(e -> headerChanged.forward());
        buttonMonth.addActionListener(e -> headerChanged.monthSelected());
        buttonYear.addActionListener(e -> headerChanged.yearSelected());

        add(cmdBack);
        add(buttonMonth);
        add(buttonYear);
        add(cmdNext);
        setDate(10, 2023);
    }

    private JButton createButton() {
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

    private Icon createIcon(String name) {
        FlatSVGIcon icon = new FlatSVGIcon("raven/datetime/icon/" + name);

        return icon;
    }

    private JButton buttonMonth;
    private JButton buttonYear;

    public interface EventHeaderChanged {
        void back();

        void forward();

        void monthSelected();

        void yearSelected();
    }
}
