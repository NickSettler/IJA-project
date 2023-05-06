package ija.ija2022.project.ui;

import ija.ija2022.project.game.GameController;
import ija.ija2022.project.game.KeyboardController;
import ija.ija2022.project.game.MouseController;

import javax.swing.*;
import java.awt.Window;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameView extends JFrame {
    private GameController controller;

    public GameView(GameController controller) {
        super("Play Game");
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setSize(500, 500);
        this.addKeyListener(KeyboardController.getInstance());
        this.addMouseListener(MouseController.getInstance());
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                handleWindowClosing(e.getWindow());
            }
        });
    }

    public void handleWindowClosing(Window window) {
        this.removeKeyListener(KeyboardController.getInstance());
        this.removeMouseListener(MouseController.getInstance());

        this.controller.handleWindowClose(window);
        this.controller = null;
    }
}
