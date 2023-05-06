package ija.ija2022.project.fields;

import ija.ija2022.project.common.ComponentView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FloorView implements ComponentView {
    private final FieldView parent;

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
        double diameter = Math.min(h, w);
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(floorImage, (int) x, (int) y, (int) diameter, (int) diameter, null);

        g2.setColor(Color.black);
        g2.setFont(new Font("Serif", Font.BOLD, 20));
    }
}
