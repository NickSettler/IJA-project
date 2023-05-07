package ija.ija2022.project.theming;

import ija.ija2022.project.fields.CommonField;

public class Theme {
    private final String directoryName;

    public Theme(String directoryName) {
        this.directoryName = "/themes/" + directoryName;
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
}
