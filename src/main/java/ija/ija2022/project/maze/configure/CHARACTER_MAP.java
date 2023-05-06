package ija.ija2022.project.maze.configure;

public enum CHARACTER_MAP {
    PATH('.'),
    WALL('X'),
    PACMAN('S'),
    GHOST('G'),
    KEY('K'),
    TARGET('T');

    private final char character;

    CHARACTER_MAP(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return this.character;
    }

    public static CHARACTER_MAP from(Character character) {
        for (CHARACTER_MAP value : CHARACTER_MAP.values()) {
            if (value.getCharacter() == character) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid character");
    }
}
