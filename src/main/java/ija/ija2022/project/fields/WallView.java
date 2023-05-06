package ija.ija2022.project.fields;

import ija.ija2022.project.common.ComponentView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallView implements ComponentView {
    private FieldView parent;

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
        Math.max(h, w);
        double diameter = Math.max(h, w);
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(wallImage, (int) x, (int) y, (int) diameter, (int) diameter, null);

//        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
//        g2.setColor(Color.green);
//        g2.fill(ellipse);
        g2.setColor(Color.black);
        g2.setFont(new Font("Serif", 1, 20));
//        g2.drawString("(" + this.model.getLives() + ")", (int) (x + diameter) / 2, (int) (y + diameter + 10.0) / 2 + 5);
    }
}
