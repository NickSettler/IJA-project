package ija.ija2022.project.replay;

import ija.ija2022.project.events.EventHandler;
import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.LivesChangeEvent;
import ija.ija2022.project.events.events.UpdateReplayStepEvent;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.theming.ThemeManager;
import ija.ija2022.project.ui.controllers.KeyboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReplayView extends JFrame {
    private ReplayController controller;
    private final JToggleButton pauseResumeButton;
    private final JTextField stepTextField;
    private final JButton stepButton;
    private final JPanel heartsPanel;

    public ReplayView(ReplayController controller) {
        super("Replay Game");
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addKeyListener(KeyboardController.getInstance());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setFocusableWindowState(true);
        this.setBackground(Color.gray);
        this.getContentPane().setBackground(Color.gray);
        GridBagConstraints c = new GridBagConstraints();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(Color.gray);

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

        stepTextField = new JTextField("0");
        stepButton = new JButton("Go to step");
        stepButton.setFocusable(false);
        stepButton.addActionListener(this::stepButtonClickHandler);

        buttonsPanel.add(decreaseContinuousSpeed);
        buttonsPanel.add(stepBackward);
        buttonsPanel.add(pauseResumeButton);
        buttonsPanel.add(stepForward);
        buttonsPanel.add(increaseContinuousSpeed);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridwidth = 2;
        this.add(buttonsPanel, c);

        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(stepTextField, c);
        c.gridx = 1;
        this.add(stepButton, c);

        heartsPanel = new JPanel();
        heartsPanel.setBackground(Color.gray);
        heartsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        this.add(heartsPanel, c);

        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mazePanel.setBackground(Color.gray);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridwidth = 5;
        c.gridy = 3;
        mazePanel.add(controller.getMazeView());
        this.add(mazePanel, c);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                handleWindowClosing();
            }
        });

        EventManager.getInstance().addEventListener(this);
    }

    private void drawLives(int count) {
        this.heartsPanel.removeAll();


        ImageIcon heartIcon = new ImageIcon("src/main/resources" + ThemeManager.getInstance().getTheme().getHeartSpriteName());
        ImageIcon heartEmptyIcon = new ImageIcon("src/main/resources" + ThemeManager.getInstance().getTheme().getEmptyHeartSpriteName());

        for (int i = 0; i < this.controller.getMaxLives(); i++) {
            boolean isFull = i < count;
            heartsPanel.add(new JLabel(isFull ? heartIcon : heartEmptyIcon));
        }

        this.revalidate();
        this.repaint();
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

    private void stepButtonClickHandler(ActionEvent e) {
        int step = Integer.parseInt(stepTextField.getText());
        this.controller.jumpToStep(step);
    }

    @EventHandler
    private void handleReplayStepUpdate(UpdateReplayStepEvent event) {
        String step = event.getStep() < 0 ? "0" : String.valueOf(event.getStep());
        this.stepTextField.setText(step);
    }

    @EventHandler
    private void handleLivesChange(LivesChangeEvent event) {
        this.drawLives(event.getLives());
    }

    private void handleWindowClosing() {
        this.removeKeyListener(KeyboardController.getInstance());

        EventManager.getInstance().removeEventListener(this);

        this.controller.destroy();
        this.controller = null;
    }
}
