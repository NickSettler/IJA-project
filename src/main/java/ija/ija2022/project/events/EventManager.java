/**
 * Project event manager.
 * It is used to manage events and their listeners.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements IEventManager {
    private final Map<Class<? extends Event>, List<EventHandle>> eventHandleMap = new HashMap<>();

    private static EventManager instance;

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void addEventListener(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes().length > 0) {
                processMethod(object, method);
            }
        }
    }

    public void addSpecificEventListener(Object object, Class<? extends Event> eventClass) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes().length > 0 && method.getParameterTypes()[0].equals(eventClass)) {
                processMethod(object, method);
            }
        }
    }

    private void processMethod(Object object, Method method) {
        EventHandler eventHandler = (EventHandler) method.getDeclaredAnnotations()[0];
        if (eventHandleMap.get(method.getParameterTypes()[0]) != null) {
            eventHandleMap.get(method.getParameterTypes()[0]).add(new EventHandle(method, object, eventHandler.priority()));
            sortArrayList(eventHandleMap.get(method.getParameterTypes()[0]));
        } else {
            List<EventHandle> eventHandles = new ArrayList<>();
            eventHandles.add(new EventHandle(method, object, eventHandler.priority()));
            eventHandleMap.put((Class<? extends Event>) method.getParameterTypes()[0], eventHandles);
        }
        method.setAccessible(true);
    }

    private void sortArrayList(List<EventHandle> arrayList) {
        arrayList.sort((objOne, objTwo) -> objTwo.priority().ordinal() - objOne.priority().ordinal());
    }

    public void removeEventListener(Object object) {
        for (Map.Entry<Class<? extends Event>, List<EventHandle>> entry : eventHandleMap.entrySet()) {
            entry.getValue().removeIf(eventHandle -> eventHandle.methodClass().equals(object));
        }
    }

    public void fireEvent(Event event) {
        List<EventHandle> eventHandles;
        if ((eventHandles = eventHandleMap.get(event.getClass())) != null) {
            for (int i = 0; i < eventHandles.size(); i++) {
                EventHandle eventHandle = eventHandles.get(i);
                if (!event.isCancelled() && eventHandle.priority() != EventPriority.MONITOR) {
                    try {
                        eventHandle.method().invoke(eventHandle.methodClass(), event);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
