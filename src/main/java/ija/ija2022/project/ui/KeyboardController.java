package ija.ija2022.project.ui;

import ija.ija2022.project.tool.common.Observable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class KeyboardController implements KeyListener, Observable {

    private Set<Observer> observers = new HashSet<>();
    private Map<Integer, Boolean> keys = new HashMap<>();

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keys.put(e.getKeyCode(), true);
        this.notifyObservers();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keys.put(e.getKeyCode(), false);
        this.notifyObservers();
    }

    public Map<Integer, Boolean> getKeys() {
        return this.keys;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach((o) -> {
            o.update(this);
        });
    }
}
