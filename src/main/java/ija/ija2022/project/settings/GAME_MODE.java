package ija.ija2022.project.settings;

public enum GAME_MODE {
    STEP_BY_STEP("step-by-step"),
    CONTINUOUS("continuous");

    private final String mode;


    GAME_MODE(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return this.mode;
    }

    public static GAME_MODE fromValue(String mode) {
        for (GAME_MODE m : GAME_MODE.values()) {
            if (m.mode.equals(mode)) {
                return m;
            }
        }
        return null;
    }
}
