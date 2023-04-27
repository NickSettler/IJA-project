package ija.ija2022.project.events;

@FunctionalInterface
public interface EventCallback {
    public void call(Object... args);
}
