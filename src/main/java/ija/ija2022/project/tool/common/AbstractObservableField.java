package ija.ija2022.project.tool.common;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractObservableField implements CommonField {
    private final Set<Observable.Observer> observers = new HashSet<>();

    public AbstractObservableField() {
    }

    public void addObserver(Observable.Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observable.Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        this.observers.forEach((o) -> {
            o.update(this);
        });
    }
}
