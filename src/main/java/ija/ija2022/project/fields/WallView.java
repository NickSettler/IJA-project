package ija.ija2022.project.fields;

import ija.ija2022.project.common.ComponentView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallView implements ComponentView {
    private final FieldView parent;

    public WallView(FieldView parent) {
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage wallImage = null;
        try {
            wallImage = ImageIO.read(new File("wall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        double diameter = Math.max(h, w);
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(wallImage, (int) x, (int) y, (int) diameter, (int) diameter, null);

        g2.setColor(Color.black);
        g2.setFont(new Font("Serif", Font.BOLD, 20));
    }
}