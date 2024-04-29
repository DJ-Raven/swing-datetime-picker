package raven.datetime.component.time;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

public class PanelClock extends JPanel {

    private final EventClockChanged clockChanged;
    private boolean use24hour;
    private boolean hourSelectionView = true;
    private int hour = -1;
    private int minute = -1;

    //  graphics option
    private AnimationChange animationChange;
    private final int margin12h = 20;
    private final int margin24h = 50;
    private Color color;

    //  public method

    public void setHourAndFix(int hour) {
        if (!use24hour) {
            if (hour == 0) {
                hour = 12;
            } else if (hour > 12) {
                hour = hour - 12;
            }
        } else {
            if (hour == 24) {
                hour = 0;
            }
        }
        setHour(hour);
    }

    public void setHour(int hour) {
        if (this.hour != hour) {
            this.hour = hour;
            if (hourSelectionView) {
                animationChange.set(getAngleOf(hour, true), getTargetMargin());
            }
            clockChanged.hourChanged(hour);
            repaint();
        }
    }

    public void setMinute(int minute) {
        if (this.minute != minute) {
            this.minute = minute;
            if (!hourSelectionView) {
                if (hour == -1) {
                    setHour(12);
                }
                animationChange.set(getAngleOf(minute, false), getTargetMargin());
            }
            clockChanged.minuteChanged(minute);
            repaint();
        }
    }

    public void setHourSelectionView(boolean hourSelectionView) {
        if (this.hourSelectionView != hourSelectionView) {
            this.hourSelectionView = hourSelectionView;
            repaint();
            runAnimation();
        }
    }

    public void setUse24hour(boolean use24hour, boolean isAm) {
        if (this.use24hour != use24hour) {
            this.use24hour = use24hour;
            repaint();
            if ((hourSelectionView && hour != -1) || (!hourSelectionView && minute != -1)) {
                if (use24hour) {
                    if (!isAm) {
                        if (hour < 12) {
                            setHourAndFix(hour + 12);
                        }
                    } else if (hour == 12) {
                        setHourAndFix(0);
                    }
                } else {
                    clockChanged.amPmChanged(hour < 12);
                    if (hour == 0) {
                        setHour(12);
                    } else if (hour > 12) {
                        setHour(hour - 12);
                    }
                }
            }
        }
    }

    public boolean isUse24hour() {
        return use24hour;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public PanelClock(EventClockChanged clockChanged) {
        this.clockChanged = clockChanged;
        init();
    }

    private void init() {
        animationChange = new AnimationChange(this);
        putClientProperty(FlatClientProperties.STYLE, "" +
                "border:5,15,5,15;" +
                "background:null;" +
                "foreground:contrast($Component.accentColor,$Panel.background,#fff)");
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseChanged(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (hourSelectionView) {
                    hourSelectionView = false;
                    clockChanged.hourMinuteChanged(false);
                    runAnimation();
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseChanged(e);
            }

            private void mouseChanged(MouseEvent e) {
                if (hourSelectionView) {
                    int hour = getValueOf(e.getPoint(), hourSelectionView);
                    setHour(hour);
                } else {
                    int minute = getValueOf(e.getPoint(), hourSelectionView);
                    setMinute(minute);
                }
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        FlatUIUtils.setRenderingHints(g2);
        Insets insets = getInsets();
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);

        int size = Math.min(width, height);
        g2.translate(insets.left, insets.top);
        int x = (width - size) / 2;
        int y = (height - size) / 2;

        //  create clock background
        g2.setColor(getClockBackground());
        g2.fill(new Ellipse2D.Double(x, y, size, size));

        //  create selection
        paintSelection(g2, x, y, size);

        //  create clock number
        paintClockNumber(g2, x, y, size);
        g2.dispose();
    }

    protected void paintSelection(Graphics2D g2, int x, int y, int size) {
        AffineTransform tran = g2.getTransform();
        size = size / 2;
        final float margin = UIScale.scale(animationChange.getMargin());
        float centerSize = UIScale.scale(8f);
        float lineSize = UIScale.scale(3);
        float selectSize = UIScale.scale(25f);
        float unselectSize = UIScale.scale(4);
        float lineHeight = size - margin;
        Area area = new Area(new Ellipse2D.Float(x + size - (centerSize / 2), y + size - (centerSize / 2), centerSize, centerSize));
        if ((hourSelectionView && hour != -1) || (!hourSelectionView && minute != -1)) {
            area.add(new Area(new RoundRectangle2D.Float(x + size - (lineSize / 2), y + margin, lineSize, lineHeight, lineSize, lineSize)));
            area.add(new Area(new Ellipse2D.Float(x + size - (selectSize / 2), y + margin - selectSize / 2, selectSize, selectSize)));
            if (!hourSelectionView && !animationChange.isRunning() && (minute % 5 != 0)) {
                area.subtract(new Area(new Ellipse2D.Float(x + size - (unselectSize / 2), y + margin - unselectSize / 2, unselectSize, unselectSize)));
            }
        }
        g2.setColor(getSelectedColor());
        float angle = animationChange.getAngle();
        g2.rotate(Math.toRadians(angle), x + size, y + size);
        g2.fill(area);
        g2.setTransform(tran);
    }

    protected void paintClockNumber(Graphics2D g2, int x, int y, int size) {
        paintClockNumber(g2, x, y, size, margin12h, 0, hourSelectionView ? 1 : 5);
        if (hourSelectionView && use24hour) {
            paintClockNumber(g2, x, y, size, margin24h, 12, 1);
        }
    }

    protected void paintClockNumber(Graphics2D g2, int x, int y, int size, int margin, int start, int add) {
        final int mg = UIScale.scale(margin);
        float center = size / 2f;
        float angle = 360 / 12;
        for (int i = 1; i <= 12; i++) {
            float ag = angle * i - 90;
            int num = fixHour((start + i * add), hourSelectionView);
            float nx = (float) (center + (Math.cos(Math.toRadians(ag)) * (center - mg)));
            float ny = (float) (center + (Math.sin(Math.toRadians(ag)) * (center - mg)));
            paintNumber(g2, x + nx, y + ny, fixNumberAndToString(num), isSelected(num));
        }
    }

    protected void paintNumber(Graphics2D g2, float x, float y, String num, boolean isSelected) {
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D rec = fm.getStringBounds(num, g2);
        x -= rec.getWidth() / 2;
        y -= rec.getHeight() / 2;
        if (isSelected) {
            g2.setColor(getSelectedForeground());
        } else {
            g2.setColor(UIManager.getColor("Panel.foreground"));
        }
        g2.drawString(num, x, y + fm.getAscent());
    }

    protected Color getClockBackground() {
        if (FlatLaf.isLafDark()) {
            return ColorFunctions.lighten(getBackground(), 0.03f);
        } else {
            return ColorFunctions.darken(getBackground(), 0.03f);
        }
    }

    protected boolean isSelected(int num) {
        if (hourSelectionView) {
            return num == hour;
        } else {
            return num == minute;
        }
    }

    protected Color getSelectedColor() {
        if (color != null) {
            return color;
        }
        return UIManager.getColor("Component.accentColor");
    }

    protected Color getSelectedForeground() {
        return getForeground();
    }

    /**
     * Convert angle to hour or minute base on the hourView
     * Return value hour or minute
     */
    private int getValueOf(float angle, boolean hourView) {
        float ag = angle / 360;
        int value = (int) (ag * (hourView ? 12 : 60));
        if (hourView) {
            return value == 0 ? 12 : value;
        } else {
            return value == 60 ? 0 : value;
        }
    }

    /**
     * Convert point location to the value hour or minute base on the hourView
     * Return value hour or minute
     */
    private int getValueOf(Point point, boolean hourView) {
        float angle = getAngleOf(point) + (hourView ? 360 / 12 / 2 : 360 / 60 / 2);
        int value = getValueOf(angle, hourView);
        if (hourView && use24hour && is24hourSelect(point)) {
            return fixHour(value + 12, true);
        } else {
            return value;
        }
    }

    private boolean is24hourSelect(Point point) {
        Insets insets = getInsets();
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        int size = Math.min(width, height) / 2;
        int distanceTarget = (size - UIScale.scale(margin12h + 20));
        float centerX = insets.left + size;
        float centerY = insets.top + size;
        double distance = Math.sqrt(Math.pow((point.x - centerX), 2) + Math.pow((point.y - centerY), 2));
        return distance < distanceTarget;
    }

    /**
     * Convert hour or minute to the angle base on the hourView
     * Return angle vales
     */
    private float getAngleOf(int number, boolean hourView) {
        float ag = 360 / (hourView ? 12 : 60);
        return fixAngle(ag * number);
    }

    /**
     * Convert point location to angle
     * Return angle
     */
    private float getAngleOf(Point point) {
        Insets insets = getInsets();
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        float centerX = insets.left + width / 2;
        float centerY = insets.top + height / 2;
        float x = point.x - centerX;
        float y = point.y - centerY;
        double angle = Math.toDegrees(Math.atan2(y, x)) + 90;
        if (angle < 0) {
            angle += 360;
        }
        return (float) angle;
    }

    /**
     * Make the angle is between 0 and 360-1
     */

    private float fixAngle(float angle) {
        if (angle > 360) {
            angle -= 360;
        }
        if (angle == 360) {
            return 0;
        }
        return angle;
    }

    /**
     * Fix hour or minute base on the hourView
     * If 24h ( return 0 to 23 )
     * If 12h ( return 1 to 12 )
     * If minute ( return 0 to 59 )
     */
    private int fixHour(int value, boolean hourView) {
        if (hourView) {
            if (use24hour) {
                if (value == 24) {
                    return 0;
                }
            }
        } else {
            if (value == 60) {
                return 0;
            }
        }
        return value;
    }

    private String fixNumberAndToString(int num) {
        if (num == 0) {
            return "00";
        }
        return num + "";
    }

    private boolean is24hour() {
        return use24hour && (hour == 0 || hour > 12);
    }

    private int getTargetMargin() {
        return is24hour() && hourSelectionView ? margin24h : margin12h;
    }

    /**
     * Start animation selection change
     */
    private void runAnimation() {
        float angleTarget = getAngleOf(hourSelectionView ? hour : minute, hourSelectionView);
        float marginTarget = getTargetMargin();
        animationChange.start(angleTarget, marginTarget);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected interface EventClockChanged {
        void hourChanged(int hour);

        void minuteChanged(int minute);

        void hourMinuteChanged(boolean isHour);

        void amPmChanged(boolean isAm);
    }
}
