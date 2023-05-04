package ija.ija2022.project.ui;

import ija.ija2022.project.game.ReplayController;
import ija.ija2022.project.settings.GAME_MODE;

import javax.swing.*;

public class ReplayView extends JPanel {
    private Timer timer;

    public ReplayView(JPanel panel, String filePath) {
        ReplayController replayController = new ReplayController(GAME_MODE.STEP_BY_STEP, filePath);

        JButton fasterButton = new JButton(">>");
        JButton slowerButton = new JButton("<<");
        JToggleButton pauseResumeButton = new JToggleButton("▶");
        fasterButton.addActionListener(e -> timer.setDelay(timer.getDelay() / 2));

        slowerButton.addActionListener(e -> timer.setDelay(timer.getDelay() * 2));

        pauseResumeButton.addActionListener(e -> {
            if (pauseResumeButton.isSelected()) {
                pauseResumeButton.setText("⏹");
                timer.start();
            } else {
                pauseResumeButton.setText("▶");
                timer.stop();
            }
        });

        panel.add(slowerButton);
        panel.add(pauseResumeButton);
        panel.add(fasterButton);

        timer = new Timer(500, e -> {
            // some logic for updating game state
        });
        timer.start();

        replayController.start();
    }
}
