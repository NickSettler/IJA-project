package ija.ija2022.project.events.events;

import ija.ija2022.project.events.Event;

public class MousePressedEvent extends Event {
    private final int x;
    private final int y;

    public MousePressedEvent(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
