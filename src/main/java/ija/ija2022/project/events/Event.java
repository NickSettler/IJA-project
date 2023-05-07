/**
 * Basic event class for project. It is used as a base class for all other events.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.events;

public abstract class Event {
    private boolean isCancelled;

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
