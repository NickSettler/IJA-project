package ija.ija2022.project.ui_controllers;

import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.KeyDownEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyboardController implements KeyListener {
    private static KeyboardController instance = null;
    private final Map<Integer, Boolean> keys;

    private KeyboardController() {
        this.keys = new HashMap<>();
    }

    public static KeyboardController getInstance() {
        if (KeyboardController.instance == null) {
            KeyboardController.instance = new KeyboardController();
        }

        return KeyboardController.instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
        EventManager.getInstance().fireEvent(new KeyDownEvent(e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public Map<Integer, Boolean> getKeys() {
        return keys;
    }
}
