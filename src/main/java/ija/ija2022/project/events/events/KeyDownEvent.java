/**
 * Project event for key down event.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
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
