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
