package raven.swing.slider;

import java.awt.*;

public abstract class SliderTransition {

    public abstract void renderImageOld(Graphics g, Image image, int width, int height, float animate);

    public abstract void renderImageNew(Graphics g, Image image, int width, int height, float animate);

    public void render(Graphics g, Image imageOld, Image imageNew, int width, int height, float animate) {
        renderImageOld(g.create(), imageOld, width, height, animate);
        renderImageNew(g.create(), imageNew, width, height, animate);
    }
}
