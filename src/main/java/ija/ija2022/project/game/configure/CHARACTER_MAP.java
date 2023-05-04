package ija.ija2022.project.game.configure;

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
}
