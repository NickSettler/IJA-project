package ija.ija2022.project.settings;

import org.json.*;

import java.io.*;

public class SettingsController {
    private final SettingsModel model;

    public SettingsController() {
        this.model = new SettingsModel();
        this.loadSettings();
    }

    private void loadSettings() {
        FileInputStream is = null;
        try {
            is = new FileInputStream("data/settings.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        String gameMode = object.getString("mode");

        this.model.setGameMode(GAME_MODE.fromValue(gameMode));
    }

    public void setContinuousMode() {
        model.setGameMode(GAME_MODE.CONTINUOUS);
    }

    public void setStepByStepMode() {
        model.setGameMode(GAME_MODE.STEP_BY_STEP);
    }

    public void saveSettings() {
        JSONObject object = new JSONObject();
        object.put("mode", model.getGameMode().toString());

        FileOutputStream os = null;
        try {
            os = new FileOutputStream("data/settings.json");
        } catch (FileNotFoundException e) {
            System.out.println("Settings file not found");
        }

        try {
            assert os != null;
            os.write(object.toString().getBytes());
            os.close();
        } catch (IOException e) {
            System.out.println("Error while saving settings");
        }
    }

    public GAME_MODE getGameMode() {
        return model.getGameMode();
    }
}
