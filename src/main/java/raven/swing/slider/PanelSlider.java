package raven.swing.slider;

import com.formdev.flatlaf.util.Animator;
import com.formdev.flatlaf.util.CubicBezierEasing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;

public class PanelSlider extends JLayeredPane {

    public Component getSlideComponent() {
        return slideComponent;
    }

    private PanelSnapshot panelSnapshot;
    private Component slideComponent;

    public PanelSlider() {
        init();
    }

    private void init() {
        panelSnapshot = new PanelSnapshot();
        setLayout(new CardLayout());
        setLayer(panelSnapshot, JLayeredPane.DRAG_LAYER);
        add(panelSnapshot);
        panelSnapshot.setVisible(false);
    }

    public void addSlide(Component component, SliderTransition transition) {
        this.slideComponent = component;
        if (getComponentCount() == 1) {
            add(component);
            repaint();
            revalidate();
            component.setVisible(true);
        } else {
            Component oldComponent = getComponent(1);
            add(component);
            if (transition != null) {
                doLayout();
                component.doLayout();
                Image oldImage = createImage(oldComponent);
                Image newImage = createImage(component);
                remove(oldComponent);
                panelSnapshot.animate(component, transition, oldImage, newImage);
            } else {
                component.setVisible(true);
                remove(oldComponent);
                revalidate();
                repaint();
            }
        }
    }

    private Image createImage(Component component) {
        VolatileImage snapshot = component.createVolatileImage(getWidth(), getHeight());
        if (snapshot != null) {
            component.paint(snapshot.getGraphics());
        }
        return snapshot;
    }

    private class PanelSnapshot extends JComponent {

        private Animator animator;
        private Component component;
        private float animate;

        private SliderTransition sliderTransition;
        private Image oldImage;
        private Image newImage;

        public PanelSnapshot() {
            animator = new Animator(400, new Animator.TimingTarget() {
                @Override
                public void timingEvent(float v) {
                    animate = v;
                    repaint();
                }

                @Override
                public void end() {
                    setVisible(false);
                    component.setVisible(true);
                    if (newImage != null) {
                        newImage.flush();
                    }
                    if (oldImage != null) {
                        oldImage.flush();
                    }
                }
            });
            animator.setInterpolator(CubicBezierEasing.EASE);
        }

        protected void animate(Component component, SliderTransition sliderTransition, Image oldImage, Image newImage) {
            if (animator.isRunning()) {
                animator.stop();
            }
            this.component = component;
            this.oldImage = oldImage;
            this.newImage = newImage;
            this.sliderTransition = sliderTransition;
            this.animate = 0f;
            repaint();
            setVisible(true);
            animator.start();
        }

        @Override
        public void paint(Graphics g) {
            if (sliderTransition != null) {
                int width = getWidth();
                int height = getHeight();
                sliderTransition.render(g, oldImage, newImage, width, height, animate);
            }
        }
    }
}
