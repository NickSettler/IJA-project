/**
 * Interface for event manager.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.events;

public interface IEventManager {

    void addEventListener(Object object);

    void addSpecificEventListener(Object object, Class<? extends Event> eventClass);

    void removeEventListener(Object object);

    void fireEvent(Event event);

}
