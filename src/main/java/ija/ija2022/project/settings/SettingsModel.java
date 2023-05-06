package ija.ija2022.project.settings;

//import org.json.simple.parser.JSONParser;

public class SettingsModel {
    private GAME_MODE gameMode;
    private int maxLives;

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
}
