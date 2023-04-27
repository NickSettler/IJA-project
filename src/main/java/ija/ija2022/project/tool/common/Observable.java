package ija.ija2022.project.tool.common;

public interface Observable {
    void addObserver(Observer var1);

    void removeObserver(Observer var1);

    void notifyObservers();

    public interface Observer {
        void update(Observable var1);
    }
}
