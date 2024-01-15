package raven.datetime.component.date;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class PanelDateOption extends JPanel {

    public PanelDateOption() {
        init();
        //      setBackground(new Color(155, 110, 110));
    }

    private void init() {
        setLayout(new MigLayout("wrap,insets 5,fillx", "[fill]","[][][][][][][]push[]"));
        add(new JSeparator(SwingConstants.VERTICAL), "dock west");
        buttonGroup = new ButtonGroup();
        add(createButton("Today"));
        add(createButton("Yesterday"));
        add(createButton("Last 7 Days"));
        add(createButton("Last 30 Days"));
        add(createButton("This Month"));
        add(createButton("Last Month"));
        add(createButton("Last Year"));

        add(createButton("Custom"));
    }

    private JToggleButton createButton(String name) {
        JToggleButton button = new JToggleButton(name);
        button.setHorizontalAlignment(SwingConstants.LEADING);
        button.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:10;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "margin:4,10,4,10;" +
                "background:null");
        buttonGroup.add(button);
        return button;
    }

    private ButtonGroup buttonGroup;
}
