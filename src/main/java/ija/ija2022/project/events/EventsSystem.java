package ija.ija2022.project.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EventsSystem {
    private static EventsSystem instance = null;

    private Map<EVENTS, List<Function<Object, Object>>> events;

    private EventsSystem() {
        this.events = new HashMap<>();
    }

    public static EventsSystem getInstance() {
        if (EventsSystem.instance == null) {
            EventsSystem.instance = new EventsSystem();
        }

        return EventsSystem.instance;
    }

    public void on(EVENTS event, Function<Object, Object> callback) {
        if (events.containsKey(event)) {
            events.get(event).add(callback);
        } else {
            events.put(event, List.of(callback));
        }
    }

    public void off(EVENTS event) {
        events.remove(event);
    }

    public void off(EVENTS event, Function<Object, Object> callback) {
        if (events.containsKey(event)) {
            events.get(event).remove(callback);
        }
    }

    public void emit(EVENTS event, Object... args) {
        if (events.containsKey(event)) {
            for (Function<Object, Object> callback : events.get(event)) {
                callback.apply(args);
            }
        }
    }
}
