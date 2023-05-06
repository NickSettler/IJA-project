package ija.ija2022.project.objects;

import ija.ija2022.project.common.ComponentView;
import ija.ija2022.project.fields.FieldView;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PacmanPathView implements ComponentView {
    private final FieldView parent;

    public PacmanPathView(FieldView parent, CommonMazeObject m) {
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
        g2.setColor(Color.blue);
        g2.fill(ellipse);
    }
}
