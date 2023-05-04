package ija.ija2022.project.ui;

import ija.ija2022.project.game.GameController;
import ija.ija2022.project.game.KeyboardController;
import ija.ija2022.project.game.WindowController;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    public GameView(GameController controller) {
        super("Play Game");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setSize(500, 500);
        this.addKeyListener(KeyboardController.getInstance());
        this.addWindowListener(WindowController.getInstance());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setFocusableWindowState(true);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        this.add(controller.getMazeView(), c);
    }
}
