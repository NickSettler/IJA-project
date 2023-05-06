package ija.ija2022.project.settings;

import javax.swing.*;
import java.awt.*;

public class SettingsView {
    private final SettingsController controller;

    private final JFrame settingsFrame;

    public SettingsView() {
        this.controller = new SettingsController();
        this.settingsFrame = new JFrame("Settings");
        this.settingsFrame.setSize(400, 400);
        this.settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.settingsFrame.setLayout(new BoxLayout(this.settingsFrame.getContentPane(), BoxLayout.Y_AXIS));
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JLabel gameModeLabel = new JLabel("Game Mode");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = c.gridy = 0;
        settingsPanel.add(gameModeLabel, c);

        GAME_MODE selectedGameMode = this.controller.getGameMode();

        JPanel gameModeButtonsPanel = new JPanel();
        gameModeButtonsPanel.setLayout(new BoxLayout(gameModeButtonsPanel, BoxLayout.Y_AXIS));
        JRadioButton stepByStepButton = new JRadioButton("Step by step", selectedGameMode == GAME_MODE.STEP_BY_STEP);
        stepByStepButton.addActionListener(e -> this.controller.setStepByStepMode());
        JRadioButton continuousButton = new JRadioButton("Continuous", selectedGameMode == GAME_MODE.CONTINUOUS);
        continuousButton.addActionListener(e -> this.controller.setContinuousMode());
        ButtonGroup gameModeButtonGroup = new ButtonGroup();
        gameModeButtonGroup.add(stepByStepButton);
        gameModeButtonGroup.add(continuousButton);
        gameModeButtonsPanel.add(stepByStepButton);
        gameModeButtonsPanel.add(continuousButton);
        c.gridx = 1;
        settingsPanel.add(gameModeButtonsPanel, c);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            this.controller.saveSettings();
            this.dispose();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        settingsPanel.add(saveButton, c);

        this.settingsFrame.add(settingsPanel);
    }

    public JFrame getFrame() {
        return this.settingsFrame;
    }

    public void dispose() {
        this.settingsFrame.dispose();
    }
}