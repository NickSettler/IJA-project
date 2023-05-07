package ija.ija2022.project.settings;

import ija.ija2022.project.theming.THEME_NAMES;

public class SettingsModel {
    private GAME_MODE gameMode;
    private int maxLives;
    private int freezeSteps;
    private THEME_NAMES theme;

    public GAME_MODE getGameMode() {
        return gameMode;
    }

    public void setGameMode(GAME_MODE gameMode) {
        this.gameMode = gameMode;
    }

    public void setMaxLives(int lives) {
        this.maxLives = lives;
    }

    public int getMaxLives() {
        return this.maxLives;
    }

    public int getFreezeSteps() {
        return freezeSteps;
    }

    public void setFreezeSteps(int freezeSteps) {
        this.freezeSteps = freezeSteps;
    }

    public void setTheme(THEME_NAMES theme) {
        this.theme = theme;
    }

    public THEME_NAMES getTheme() {
        return this.theme;
    }
}
