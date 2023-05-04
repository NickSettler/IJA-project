package ija.ija2022.project.ui;

import ija.ija2022.project.game.KeyboardController;
import ija.ija2022.project.game.ReplayController;
import ija.ija2022.project.game.WindowController;
import ija.ija2022.project.settings.GAME_MODE;

import javax.swing.*;
import java.awt.*;

public class ReplayView extends JFrame {
    public ReplayView(ReplayController controller) {
        super("Replay Game");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setSize(500, 500);
        this.addKeyListener(KeyboardController.getInstance());
        this.addWindowListener(WindowController.getInstance());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setFocusableWindowState(true);
        GridBagConstraints c = new GridBagConstraints();

        JButton fasterButton = new JButton(">>");
        fasterButton.setFocusable(false);
        JButton slowerButton = new JButton("<<");
        slowerButton.setFocusable(false);
        JToggleButton pauseResumeButton = new JToggleButton("▶");
        pauseResumeButton.setFocusable(false);
//        fasterButton.addActionListener(e -> timer.setDelay(timer.getDelay() / 2));

//        slowerButton.addActionListener(e -> timer.setDelay(timer.getDelay() * 2));

        pauseResumeButton.addActionListener(e -> {
            if (controller.getMode() == GAME_MODE.CONTINUOUS) {
                pauseResumeButton.setText("▶");
                controller.setMode(GAME_MODE.STEP_BY_STEP);
                controller.stop();
            } else {
                pauseResumeButton.setText("⏹");
                controller.setMode(GAME_MODE.CONTINUOUS);
                controller.start();
            }
        });

        c.weighty = 0.1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(slowerButton, c);
        c.gridx = 1;
        this.add(pauseResumeButton, c);
        c.gridx = 2;
        this.add(fasterButton, c);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.9;
        c.weightx = 1;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        this.add(controller.getMazeView(), c);
    }
}
