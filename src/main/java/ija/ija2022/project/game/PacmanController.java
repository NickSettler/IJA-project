package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class PacmanController implements KeyListener {
    private PacmanObject pacmanObject;

    public PacmanController(PacmanObject pacmanObject) {
        this.pacmanObject = pacmanObject;
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

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
}