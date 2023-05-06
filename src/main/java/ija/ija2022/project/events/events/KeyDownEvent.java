package ija.ija2022.project.events.events;

import ija.ija2022.project.events.Event;

public class KeyDownEvent extends Event {
    private final Integer keyCode;

    public KeyDownEvent(Integer _keyCode) {
        keyCode = _keyCode;
    }

    public Integer getKeyCode() {
        return keyCode;
    }
}
