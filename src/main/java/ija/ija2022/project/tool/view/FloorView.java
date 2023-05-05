package ija.ija2022.project.tool.view;

import ija.ija2022.project.tool.common.CommonMazeObject;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FloorView implements ComponentView {
    private FieldView parent;

    public FloorView(FieldView parent) {
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage floorImage = null;
        try {
            floorImage = ImageIO.read(new File("floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w);
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(floorImage, (int) x, (int) y,  (int) diameter, (int) diameter, null);

//        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
//        g2.setColor(Color.green);
//        g2.fill(ellipse);
        g2.setColor(Color.black);
        g2.setFont(new Font("Serif", 1, 20));
//        g2.drawString("(" + this.model.getLives() + ")", (int) (x + diameter) / 2, (int) (y + diameter + 10.0) / 2 + 5);
    }
}
