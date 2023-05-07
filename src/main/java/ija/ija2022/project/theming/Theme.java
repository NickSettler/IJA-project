/**
 * Represents a theme of the game.
 * It is used to get address of the images to be used in the game.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.theming;

import ija.ija2022.project.fields.CommonField;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class Theme {
    private final String directoryName;
    private String baseColor;

    public Theme(String directoryName) {
        this.directoryName = "/themes/" + directoryName;

        this.processConfig();
    }

    private void processConfig() {
        InputStream is = ThemeManager.class.getResourceAsStream(this.directoryName + "/config.json");

        if (is == null) {
            throw new RuntimeException("Theme config file not found");
        }

        JSONObject object = new JSONObject(new JSONTokener(is));

        this.baseColor = object.getString("baseColor");
    }

    public String getWallSpriteName() {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.WALL.name().toLowerCase() + ".png";
    }

    public String getFloorSpriteName() {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.FLOOR.name().toLowerCase() + ".png";
    }

    public String getPacmanSpriteName(CommonField.Direction direction) {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.PACMAN.name().toLowerCase() + "_" + direction.getChar() + ".png";
    }

    public String getGhostSpriteName(CommonField.Direction direction) {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.GHOST.name().toLowerCase() + "_" + direction.getChar() + ".png";
    }

    public String getGhostSpriteName(boolean frozen) {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.GHOST.name().toLowerCase() + "_" + (frozen ? "f" : "n") + ".png";
    }

    public String getEmptyHeartSpriteName() {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.HEART_EMPTY.name().toLowerCase() + ".png";
    }

    public String getHeartSpriteName() {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.HEART.name().toLowerCase() + ".png";
    }

    public String getClockSpriteName() {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.CLOCK.name().toLowerCase() + ".png";
    }

    public String getKeySpriteName() {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.KEY.name().toLowerCase() + ".png";
    }

    public String getTargetSpriteName(boolean opened) {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.TARGET.name().toLowerCase() + "_" + (opened ? "o" : "c") + ".png";
    }

    public String getBackgroundImageName() {
        return this.directoryName + "/" + THEME_OBJECT_NAMES_PATTERNS.BACKGROUND.name().toLowerCase() + ".jpg";
    }

    public String getBaseColor() {
        return baseColor;
    }
}
