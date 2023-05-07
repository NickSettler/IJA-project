/**
 * Project event for update replay step event.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.events.events;

import ija.ija2022.project.events.Event;

public class UpdateReplayStepEvent extends Event {
    private final int step;

    public UpdateReplayStepEvent(int _step) {
        step = _step;
    }

    public int getStep() {
        return this.step;
    }
}
