package ija.ija2022.project;

import ija.ija2022.project.game.GameController;
import ija.ija2022.project.replay.ReplayController;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.settings.SettingsView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Window extends JFrame {

    public Window() {
        super("Pacman game");
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setFocusableWindowState(true);
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        JPanel panel = new JPanel();

        setGhostImage();
        createButtons(panel);

        this.add(panel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void createButtons(JPanel panel) {
        JButton startButton = new JButton("start game");
        panel.add(startButton);
        JButton replayButton = new JButton("replay game");
        panel.add(replayButton);
        JButton settingsButton = new JButton("settings");
        panel.add(settingsButton);
        JButton endButton = new JButton("exit game");
        panel.add(endButton);

        replayButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("data/");

            int result = fileChooser.showOpenDialog(Window.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                new ReplayController(GAME_MODE.STEP_BY_STEP, selectedFile.getAbsolutePath());
            }
        });

        startButton.addActionListener(e -> {
            int index = JOptionPane.showOptionDialog(
                    Window.this,
                    "Select a mode of play:",
                    "Game mode",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    GAME_MODE.values(),
                    GAME_MODE.STEP_BY_STEP
            );
            GAME_MODE selectedMode = GAME_MODE.values()[index];

            JFileChooser fileChooser = new JFileChooser("maps/");

            int result = fileChooser.showOpenDialog(Window.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                new GameController(selectedMode, selectedFile.getAbsolutePath());
            }
        });

        settingsButton.addActionListener(e -> new SettingsView().getFrame().setVisible(true));

        endButton.addActionListener(e -> System.exit(0));
    }

    private void setGhostImage() {
        ImageIcon imageIcon = new ImageIcon("ghost.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setIcon(imageIcon);
        this.add(imageLabel, BorderLayout.CENTER);
    }
}