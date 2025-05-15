package test.renderer;

import java.awt.*;

public enum LabelDate {

    FREE("Free", new Color(59, 155, 60), 1f),
    MOSTLY_BOOKED("Mostly Booked", new Color(239, 138, 138), 0.75f),
    SOME_SLOTS("Some Slots", new Color(213, 189, 44), 0.5f),
    FULLY_BOOKED("Fully Booked", new Color(231, 41, 41), 1f),
    MOSTLY_FREE("Mostly Free", new Color(140, 225, 141), 0.75f);

    private final String name;
    private final Color color;
    private final float value;

    LabelDate(String name, Color color, float value) {
        this.name = name;
        this.color = color;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public float getValue() {
        return value;
    }
}
