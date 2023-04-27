package ija.ija2022.project.tool.common;

public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    public interface Observer {
        void update(Observable observable);
    }
}
