package ija.ija2022.project.theming;

public enum THEME_OBJECT_NAMES_PATTERNS {
    WALL("wall"),
    FLOOR("floor"),
    GHOST("ghost"),
    PACMAN("pacman"),
    HEART("heart"),
    CLOCK("clock"),
    KEY("key"),
    TARGET("target");

    private final String pattern;

    THEME_OBJECT_NAMES_PATTERNS(String pattern) {
        this.pattern = pattern;
    }
}
