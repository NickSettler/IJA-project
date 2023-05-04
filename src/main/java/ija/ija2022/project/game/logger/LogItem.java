package ija.ija2022.project.game.logger;

import ija.ija2022.project.game.configure.CHARACTER_MAP;
import ija.ija2022.project.tool.common.CommonField;
import javafx.util.Pair;

import java.util.Arrays;

public record LogItem(CHARACTER_MAP character, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
    public LogItem {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }
        if (from == null) {
            throw new IllegalArgumentException("From cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("To cannot be null");
        }
    }

    @Override
    public String toString() {
        return String.format("%s:(%d,%d)-(%d,%d)", this.character.getCharacter(), this.from.getKey(), this.from.getValue(), this.to.getKey(), this.to.getValue());
    }

    public CommonField.Direction direction() {
        return CommonField.Direction.from(
                this.to().getKey() - this.from().getKey(),
                this.to().getValue() - this.from().getValue()
        );
    }

    public static LogItem fromString(String string) {
        String[] parts = string.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid string format");
        }

        String[] coordinatesPairs = parts[1].split("-");

        if (coordinatesPairs.length != 2) {
            throw new IllegalArgumentException("Invalid string format");
        }

        Integer[] fromCoordinates = Arrays.stream(coordinatesPairs[0].replaceAll("[()]", "").split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        Integer[] toCoordinates = Arrays.stream(coordinatesPairs[1].replaceAll("[()]", "").split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return new LogItem(
                CHARACTER_MAP.from(parts[0].charAt(0)),
                new Pair<>(fromCoordinates[0], fromCoordinates[1]),
                new Pair<>(toCoordinates[0], toCoordinates[1])
        );
    }
}