package ija.ija2022.project.objects;

import ija.ija2022.project.common.ComponentView;
import ija.ija2022.project.fields.FieldView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PacmanView implements ComponentView {
    private final FieldView parent;
    private final PacmanObject pacman;
    private String directionChar = "r";

    public PacmanView(FieldView parent, PacmanObject pacman) {
        this.parent = parent;
        this.pacman = pacman;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage pacmanImage = null;
        try {
            directionChar = switch (this.pacman.getDirection()) {
                case U -> "u";
                case D -> "d";
                case L -> "l";
                case R -> "r";
                case N -> directionChar;
            };
            pacmanImage = ImageIO.read(getClass().getResource("/pacman/" + directionChar + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        double diameter = Math.min(h, w) - 1.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;

        g2.drawImage(pacmanImage, (int) x, (int) y, (int) diameter, (int) diameter, null);

        g2.setColor(Color.black);
        g2.setFont(new Font("Serif", Font.BOLD, 20));
    }
}
