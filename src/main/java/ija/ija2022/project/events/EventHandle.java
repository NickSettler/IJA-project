package ija.ija2022.project.events;

import java.lang.reflect.Method;

public record EventHandle(Method method, Object methodClass, EventPriority priority) {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EventHandle && ((EventHandle) obj).method().equals(method) && ((EventHandle) obj).methodClass().equals(methodClass);
    }
}