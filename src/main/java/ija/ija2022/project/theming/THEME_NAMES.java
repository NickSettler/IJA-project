package ija.ija2022.project.theming;

public enum THEME_NAMES {
    BASIC("basic");

    private final String name;

    THEME_NAMES(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static THEME_NAMES fromValue(String name) {
        for (THEME_NAMES n : THEME_NAMES.values()) {
            if (n.name.equals(name)) {
                return n;
            }
        }
        return null;
    }
}
