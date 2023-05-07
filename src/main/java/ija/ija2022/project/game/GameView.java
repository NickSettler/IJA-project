package ija.ija2022.project.game;

import ija.ija2022.project.events.EventHandler;
import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.LivesChangeEvent;
import ija.ija2022.project.ui_controllers.KeyboardController;
import ija.ija2022.project.ui_controllers.MouseController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameView extends JFrame {
    private GameController controller;
    private final JPanel heartsPanel;

    public GameView(GameController controller) {
        super("Play Game");
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addKeyListener(KeyboardController.getInstance());
        this.addMouseListener(MouseController.getInstance());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setFocusableWindowState(true);
        this.setBackground(Color.gray);
        this.getContentPane().setBackground(Color.gray);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;

        this.add(controller.getMazeView(), c);

        heartsPanel = new JPanel();
        heartsPanel.setBackground(Color.gray);
        heartsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 0.02;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 0;
        this.add(heartsPanel, c);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                handleWindowClosing(e.getWindow());
            }
        });

        EventManager.getInstance().addEventListener(this);
    }

    @EventHandler
    private void handleLivesChange(LivesChangeEvent event) {
        this.drawLives(event.getLives());
    }

    private void drawLives(int count) {
        this.heartsPanel.removeAll();

        int max = Math.max(this.controller.getMaxLives(), count);

        int heartSize = 20;

        ImageIcon heartIcon = new ImageIcon("src/main/resources/heart_full.png");
        Image image = heartIcon.getImage();
        Image newImage = image.getScaledInstance(heartSize, heartSize, java.awt.Image.SCALE_SMOOTH);
        heartIcon = new ImageIcon(newImage);
        ImageIcon heartEmptyIcon = new ImageIcon("src/main/resources/heart_empty.png");
        image = heartEmptyIcon.getImage();
        newImage = image.getScaledInstance(heartSize, heartSize, java.awt.Image.SCALE_SMOOTH);
        heartEmptyIcon = new ImageIcon(newImage);

        for (int i = 0; i < max; i++)
            heartsPanel.add(new JLabel(i < count ? heartIcon : heartEmptyIcon));

        this.revalidate();
        this.repaint();
    }

    public void handleWindowClosing(Window window) {
        this.controller.handleWindowClose(window);
    }

    @Override
    public void dispose() {
        super.dispose();

        this.heartsPanel.removeAll();

        this.removeKeyListener(KeyboardController.getInstance());
        this.removeMouseListener(MouseController.getInstance());
        EventManager.getInstance().removeEventListener(this);

        this.controller = null;
    }
}
