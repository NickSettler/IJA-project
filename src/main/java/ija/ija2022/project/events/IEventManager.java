package ija.ija2022.project.events;

public interface IEventManager {

    public void addEventListener(Object object);

    public void addSpecificEventListener(Object object, Class<? extends Event> eventClass);

    public void removeEventListener(Object object);

    public void fireEvent(Event event);

}
