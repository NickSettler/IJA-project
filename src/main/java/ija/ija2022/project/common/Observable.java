package ija.ija2022.project.common;

public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    interface Observer {
        void update(Observable observable);
    }
}
