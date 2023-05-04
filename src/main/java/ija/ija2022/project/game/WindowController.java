package ija.ija2022.project.game;

import ija.ija2022.project.events.EVENTS;
import ija.ija2022.project.events.EventsSystem;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowController implements WindowListener {
    private static WindowController instance = null;

    public static WindowController getInstance() {
        if (WindowController.instance == null) {
            WindowController.instance = new WindowController();
        }

        return WindowController.instance;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        EventsSystem.getInstance().emit(EVENTS.CLOSE_PRESENTER, null);

        e.getWindow().dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
