package ija.ija2022.project.settings;

//import org.json.simple.parser.JSONParser;

public class SettingsModel {
    private GAME_MODE gameMode;

    public SettingsModel() {
//        JSONParser parser = new JSONParser();
    }

    public GAME_MODE getGameMode() {
        return gameMode;
    }

    public void setGameMode(GAME_MODE gameMode) {
        this.gameMode = gameMode;
    }
}
