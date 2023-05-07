package ija.ija2022.project.objects;

import ija.ija2022.project.common.ComponentView;
import ija.ija2022.project.fields.FieldView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HeartView implements ComponentView {
    private final FieldView parent;

    public HeartView(FieldView parent, CommonMazeObject m) {
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage keyImage = null;
        try {
            keyImage = ImageIO.read(getClass().getResource("/heart_full.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(keyImage, (int) x, (int) y, (int) diameter, (int) diameter, null);
    }
}
