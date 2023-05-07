package ija.ija2022.project.objects;

import ija.ija2022.project.common.ComponentView;
import ija.ija2022.project.fields.FieldView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ClockView implements ComponentView {
    private final FieldView parent;

    public ClockView(FieldView parent) {
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage clockImage = null;
        try {
            clockImage = ImageIO.read(getClass().getResource("/clocks.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter);

        g2.drawImage(clockImage, (int) x, (int) y, (int) diameter, (int) diameter / 2, null);
    }
}
