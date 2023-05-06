package ija.ija2022.project.events.events;

import ija.ija2022.project.events.Event;

public class LivesChangeEvent extends Event {
    private final int lives;

    public LivesChangeEvent(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return this.lives;
    }
}
