package ija.ija2022.project.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplayView  extends JPanel {
    private Timer timer;
    public ReplayView(JPanel panel) {
        JButton fasterButton = new JButton(">>");
        JButton slowerButton = new JButton("<<");
        JToggleButton pauseResumeButton = new JToggleButton("▶");
        fasterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.setDelay(timer.getDelay() / 2);
            }
        });

        slowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.setDelay(timer.getDelay() * 2);
            }
        });

        pauseResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseResumeButton.isSelected()) {
                    pauseResumeButton.setText("⏹");
                    timer.start();
                } else {
                    pauseResumeButton.setText("▶");
                    timer.stop();
                }
            }
        });

        panel.add(slowerButton);
        panel.add(pauseResumeButton);
        panel.add(fasterButton);

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // some logic for updating game state
            }
        });
        timer.start();
    }
}
