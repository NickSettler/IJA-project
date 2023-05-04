package ija.ija2022.project.game;

import ija.ija2022.project.events.EVENTS;
import ija.ija2022.project.events.EventsSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyboardController implements KeyListener {
    private static KeyboardController instance = null;
    private Map<Integer, Boolean> keys;

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
        EventsSystem.getInstance().emit(EVENTS.KEYDOWN, e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        keys.put(e.getKeyCode(), false);
        EventsSystem.getInstance().emit(EVENTS.KEYUP, e.getKeyCode());
    }

    public Map<Integer, Boolean> getKeys() {
        return keys;
    }
}
