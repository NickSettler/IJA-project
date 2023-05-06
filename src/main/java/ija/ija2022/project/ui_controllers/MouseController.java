package ija.ija2022.project.ui_controllers;

import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.MouseClickedEvent;
import ija.ija2022.project.events.events.MousePressedEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener {
    private static MouseController instance;

    public static MouseController getInstance() {
        if (instance == null) {
            instance = new MouseController();
        }

        return instance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        EventManager.getInstance().fireEvent(new MouseClickedEvent(e.getX(), e.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        EventManager.getInstance().fireEvent(new MousePressedEvent(e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
