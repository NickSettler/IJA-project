package ija.ija2022.project.events;

import java.lang.reflect.Method;

public class EventHandle {
    private final Method method;
    private final Object methodClass;
    private final EventPriority priority;

    public EventHandle(Method method, Object methodClass, EventPriority priority) {
        this.method = method;
        this.methodClass = methodClass;
        this.priority = priority;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public Method getMethod() {
        return method;
    }

    public Object getMethodClass() {
        return methodClass;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EventHandle && ((EventHandle) obj).getMethod().equals(method) && ((EventHandle) obj).getMethodClass().equals(methodClass);
    }
}