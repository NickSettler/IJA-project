package ija.ija2022.project.replay;

import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.ui_controllers.KeyboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReplayView extends JFrame {
    public int timeChange = 0;
    private ReplayController controller;
    private final JToggleButton pauseResumeButton;

    public ReplayView(ReplayController controller) {
        super("Replay Game");
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setSize(500, 500);
        this.addKeyListener(KeyboardController.getInstance());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setFocusableWindowState(true);
        GridBagConstraints c = new GridBagConstraints();

        ImageIcon icon = new ImageIcon("src/main/resources/icons/ff.png");
        JButton increaseContinuousSpeed = new JButton(icon);
        increaseContinuousSpeed.setFocusable(false);
        JButton decreaseContinuousSpeed = new JButton("src/main/resources/icons/fr.png");
        decreaseContinuousSpeed.setFocusable(false);
        pauseResumeButton = new JToggleButton("▶");
        pauseResumeButton.setFocusable(false);

        pauseResumeButton.addActionListener(this::pauseButtonClickHandler);

        decreaseContinuousSpeed.addActionListener(e -> timeChange += 50);

        increaseContinuousSpeed.addActionListener(e -> timeChange -= 50);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                timeChange = 250;
            }

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                handleWindowClosing();
            }
        });

        c.weighty = 0.1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(decreaseContinuousSpeed, c);
        c.gridx = 1;
        this.add(pauseResumeButton, c);
        c.gridx = 2;
        this.add(increaseContinuousSpeed, c);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.9;
        c.weightx = 1;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        this.add(controller.getMazeView(), c);
    }

    public int getTimeChange() {
        return timeChange;
    }

    private void pauseButtonClickHandler(ActionEvent e) {
        if (controller.getMode() == GAME_MODE.CONTINUOUS) {
            pauseResumeButton.setText("▶");
            controller.setMode(GAME_MODE.STEP_BY_STEP);
            controller.stop();
        } else {
            pauseResumeButton.setText("⏹");
            controller.setMode(GAME_MODE.CONTINUOUS);
            controller.start();
        }
    }

    private void handleWindowClosing() {
        this.removeKeyListener(KeyboardController.getInstance());

        this.controller.destroy();
        this.controller = null;
    }
}
