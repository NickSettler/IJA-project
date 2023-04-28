package ija.ija2022.project.game;

import ija.ija2022.project.astar.AStarPathFinder;
import ija.ija2022.project.tool.common.CommonField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PacmanController extends KeyAdapter {
    private PacmanObject pacmanObject;
    private AStarPathFinder pathFinder;

    public PacmanController(PacmanObject pacmanObject, GameModel gameModel, JPanel panel) {
        this.pacmanObject = pacmanObject;
        pathFinder = new AStarPathFinder(gameModel, gameModel.getMaze());
        panel.addKeyListener(this);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO
                Point clickPoint = e.getPoint();
                List<CommonField.Direction> path = pathFinder.findPath(clickPoint, clickPoint);
            }
        });
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            pacmanObject.move(CommonField.Direction.U);
        } else if (keyCode == KeyEvent.VK_A) {
            pacmanObject.move(CommonField.Direction.L);
        } else if (keyCode == KeyEvent.VK_S) {
            pacmanObject.move(CommonField.Direction.D);
        } else if (keyCode == KeyEvent.VK_D) {
            pacmanObject.move(CommonField.Direction.R);
        }
    }
}