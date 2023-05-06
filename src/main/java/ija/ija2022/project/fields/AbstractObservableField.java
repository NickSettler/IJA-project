package ija.ija2022.project.fields;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractObservableField implements CommonField {
    private final Set<Observer> observers = new HashSet<>();

    public AbstractObservableField() {
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        this.observers.forEach((o) -> {
            o.update(this);
        });
    }
}
