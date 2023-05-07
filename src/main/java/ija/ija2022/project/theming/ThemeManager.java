package ija.ija2022.project.theming;

import ija.ija2022.project.settings.SettingsController;

public class ThemeManager {
    private static ThemeManager instance = null;

    private final SettingsController settingsController;
    private Theme currentTheme;

    private ThemeManager() {
        this.settingsController = new SettingsController();
        this.currentTheme = new Theme(this.settingsController.getTheme().getName());
    }

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }

        return instance;
    }

    public void setTheme(THEME_NAMES name) {
        this.settingsController.setTheme(name);
        this.currentTheme = new Theme(name.getName());
    }

    public Theme getTheme() {
        return this.currentTheme;
    }
}
