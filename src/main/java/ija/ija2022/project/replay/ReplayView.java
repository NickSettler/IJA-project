package ija.ija2022.project.replay;

import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.ui_controllers.KeyboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReplayView extends JFrame {
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

        ImageIcon increaseContinuousSpeedIcon = new ImageIcon("src/main/resources/icons/ff.png");
        JButton increaseContinuousSpeed = new JButton(increaseContinuousSpeedIcon);
        increaseContinuousSpeed.setFocusable(false);
        ImageIcon decreaseContinuousSpeedIcon = new ImageIcon("src/main/resources/icons/fr.png");
        JButton decreaseContinuousSpeed = new JButton(decreaseContinuousSpeedIcon);
        decreaseContinuousSpeed.setFocusable(false);
        ImageIcon stepForwardIcon = new ImageIcon("src/main/resources/icons/sn.png");
        JButton stepForward = new JButton(stepForwardIcon);
        stepForward.setFocusable(false);
        ImageIcon stepBackwardIcon = new ImageIcon("src/main/resources/icons/sp.png");
        JButton stepBackward = new JButton(stepBackwardIcon);
        stepBackward.setFocusable(false);
        ImageIcon pauseIcon = new ImageIcon("src/main/resources/icons/pa.png");
        pauseResumeButton = new JToggleButton(pauseIcon);
        pauseResumeButton.setFocusable(false);

        decreaseContinuousSpeed.addActionListener(e -> controller.increaseTickTime());

        stepBackward.addActionListener(e -> controller.previousStep());

        pauseResumeButton.addActionListener(this::pauseButtonClickHandler);

        stepForward.addActionListener(e -> controller.nextStep());

        increaseContinuousSpeed.addActionListener(e -> controller.decreaseTickTime());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                handleWindowClosing();
            }
        });

        c.weighty = 0.1;
        c.weightx = 0.1;
        c.gridy = 0;
        c.gridx = 1;
        this.add(decreaseContinuousSpeed, c);
        c.gridx = 2;
        this.add(stepBackward, c);
        c.gridx = 3;
        this.add(pauseResumeButton, c);
        c.gridx = 4;
        this.add(stepForward, c);
        c.gridx = 5;
        this.add(increaseContinuousSpeed, c);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.9;
        c.weightx = 1;
        c.gridx = 0;
        c.gridwidth = 7;
        c.gridy = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        this.add(controller.getMazeView(), c);
    }

    private void pauseButtonClickHandler(ActionEvent e) {
        ImageIcon pauseIcon = new ImageIcon("src/main/resources/icons/pa.png");
        ImageIcon playIcon = new ImageIcon("src/main/resources/icons/pl.png");
        if (controller.getMode() == GAME_MODE.CONTINUOUS) {
            pauseResumeButton.setIcon(pauseIcon);
            controller.setMode(GAME_MODE.STEP_BY_STEP);
            controller.stop();
        } else {
            pauseResumeButton.setIcon(playIcon);
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
