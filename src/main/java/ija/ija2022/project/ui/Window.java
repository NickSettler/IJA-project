package ija.ija2022.project.ui;

import ija.ija2022.project.events.EVENTS;
import ija.ija2022.project.events.EventsSystem;
import ija.ija2022.project.settings.GAME_MODE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Window extends JFrame implements IUIView, KeyListener {

    public Window() {
        super("Pacman game");
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setFocusableWindowState(true);
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        JPanel panel = new JPanel();

        setGhostImage();
        createButtons(panel);

        this.add(panel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

//            JFrame frame = new JFrame("Replay Game");
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            frame.pack();
//            frame.setLayout(new BorderLayout());
//            frame.setSize(500, 500);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//
//            JPanel replayPanel = new JPanel();
//            ReplayView replayView = new ReplayView(replayPanel);
//            replayPanel.add(replayView);
//
//            frame.add(replayPanel, BorderLayout.NORTH);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("saved_games"));

            int result = fileChooser.showOpenDialog(Window.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                int index = JOptionPane.showOptionDialog(
                        Window.this,
                        "Select a mode of replay:",
                        "Replay mode",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        GAME_MODE.values(),
                        GAME_MODE.STEP_BY_STEP
                );

                GAME_MODE selectedMode = GAME_MODE.values()[index];
                System.out.println("Selected mode: " + selectedMode);
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                JFrame frame = new JFrame("Replay Game");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLayout(new BorderLayout());
                frame.setSize(500, 500);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                JPanel replayPanel = new JPanel();
                ReplayView replayView = new ReplayView(replayPanel);
                replayPanel.add(replayView);

                frame.add(replayPanel, BorderLayout.NORTH);

                if (selectedMode != null) {
                    if (selectedMode.equals(GAME_MODE.STEP_BY_STEP)) {
                        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile))) {
                            //GameState state = (GameState)
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else if (selectedMode.equals(GAME_MODE.CONTINUOUS)) {
                        int delay = 500;
                        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile))) {
                            //
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
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
            if (selectedMode != null) {
                boolean isContinuousMode = selectedMode.equals(GAME_MODE.CONTINUOUS);
//                    GameController controller = new GameController
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

    @Override
    public JFrame getFrame() {
        return this;
    }

    @Override
    public void dispose() {
        this.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        EventsSystem.getInstance().emit(EVENTS.KEYDOWN, e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        EventsSystem.getInstance().emit(EVENTS.KEYUP, e.getKeyCode());
    }
}