package ija.ija2022.project.events.events;

import ija.ija2022.project.events.Event;

import java.awt.*;

public class WindowCloseEvent extends Event {
    private Window window;

    public WindowCloseEvent(Window _window) {
        window = _window;
    }

    public Window getWindow() {
        return window;
    }
}
