package ija.ija2022.project.tool.common;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractObservableField implements CommonField {
    private final Set<Observable.Observer> observers = new HashSet<>();

    public AbstractObservableField() {
    }

    public void addObserver(Observable.Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observable.Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        this.observers.forEach((o) -> {
            o.update(this);
        });
    }
}
