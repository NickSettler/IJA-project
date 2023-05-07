package ija.ija2022.project.settings;

import ija.ija2022.project.theming.THEME_NAMES;
import ija.ija2022.project.theming.ThemeManager;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsController {
    private final SettingsModel model;

    public SettingsController() {
        this.model = new SettingsModel();
        this.loadSettings();
    }

    private void loadSettings() {
        FileInputStream is;
        try {
            is = new FileInputStream("data/settings.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        String gameMode = object.getString("mode");
        int maxLives = object.getInt("maxLives");
        int freezeSteps = object.getInt("freezeSteps");
        String themeName = object.getString("theme");

        this.model.setGameMode(GAME_MODE.fromValue(gameMode));
        this.model.setMaxLives(maxLives);
        this.model.setFreezeSteps(freezeSteps);
        this.model.setTheme(THEME_NAMES.fromValue(themeName));
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
        object.put("maxLives", model.getMaxLives());
        object.put("freezeSteps", model.getFreezeSteps());
        object.put("theme", model.getTheme().getName());

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

    public void setMaxLives(int lives) {
        model.setMaxLives(lives);
    }

    public int getMaxLives() {
        return model.getMaxLives();
    }

    public void setFreezeSteps(int freezeSteps) {
        model.setFreezeSteps(freezeSteps);
    }

    public int getFreezeSteps() {
        return model.getFreezeSteps();
    }

    public void setTheme(THEME_NAMES theme) {
        model.setTheme(theme);
        ThemeManager.getInstance().setTheme(theme);
    }

    public THEME_NAMES getTheme() {
        return model.getTheme();
    }
}
