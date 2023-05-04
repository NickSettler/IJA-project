package ija.ija2022.project.tool.view;

import ija.ija2022.project.tool.common.CommonMazeObject;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GhostView implements ComponentView {
    private final CommonMazeObject model;
    private final FieldView parent;

    public GhostView(FieldView parent, CommonMazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage pacmanImage = null;
        try {
            pacmanImage = ImageIO.read(new File("ghost2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(pacmanImage, (int) x, (int) y,  (int) diameter, (int) diameter, null);
//        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
//        g2.setColor(Color.yellow);
//        g2.fill(ellipse);
    }
}
