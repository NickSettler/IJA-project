package ija.ija2022.project.game.configure;

import ija.ija2022.project.game.*;
import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.common.CommonMazeObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MazeConfigure {
    private static final Map<Character, Class<?>> FIELDS_MAP = new HashMap<>() {{
        put(CHARACTER_MAP.PATH.getCharacter(), PathField.class);
        put(CHARACTER_MAP.WALL.getCharacter(), WallField.class);
        put(CHARACTER_MAP.KEY.getCharacter(), WallField.class);
        put(CHARACTER_MAP.TARGET.getCharacter(), WallField.class);
    }};

    private static final Map<Character, Class<?>> OBJECTS_MAP = new HashMap<>() {{
        put(CHARACTER_MAP.PACMAN.getCharacter(), PacmanObject.class);
        put(CHARACTER_MAP.GHOST.getCharacter(), GhostObject.class);
    }};

    private boolean reading;

    private int rowCounter;

    private CommonMaze commonMaze;

    public MazeConfigure() {
        this.reading = false;
        this.rowCounter = 1;
        this.commonMaze = null;
    }

    private boolean checkLine(String line) {
        if (line == null) return false;

        if (line.length() != this.commonMaze.numCols() - 2) return false;

        String allowedChars = Arrays.stream(CHARACTER_MAP.values())
                .map(CHARACTER_MAP::getCharacter)
                .map(String::valueOf)
                .reduce((a, b) -> a + b).orElse("");

        return line.matches("^[ " + allowedChars + "]+$");
    }

    public CommonMaze createMaze() {
        if (this.reading) {
            return null;
        }

        if (this.commonMaze.getFields() == null) {
            return null;
        }

        if (this.rowCounter != this.commonMaze.numRows()) {
            return null;
        }

        return this.commonMaze;
    }

    public void processLine(String line) {
        if (!this.reading) return;

        if (!this.checkLine(line)) return;

        if (this.rowCounter >= this.commonMaze.numRows()) {
            this.rowCounter++;
            return;
        }

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            boolean hasField = FIELDS_MAP.containsKey(ch);
            boolean hasObject = OBJECTS_MAP.containsKey(ch);

            if (hasField) {
                try {
                    CommonField field = (CommonField) FIELDS_MAP.get(ch).getConstructor(int.class, int.class).newInstance(this.rowCounter, i + 1);
                    this.commonMaze.setField(this.rowCounter, i + 1, field);
                    field.setMaze(this.commonMaze);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (hasObject) {
                try {
                    PathField field = new PathField(this.rowCounter, i + 1);
                    this.commonMaze.setField(this.rowCounter, i + 1, field);
                    field.setMaze(this.commonMaze);
                    CommonMazeObject object = (CommonMazeObject) OBJECTS_MAP.get(ch).getConstructor(int.class, int.class, CommonMaze.class).newInstance(this.rowCounter, i + 1, this.commonMaze);
                    this.commonMaze.putObject(object, this.rowCounter, i + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        this.rowCounter++;
    }

    public void startReading(int rows, int cols) {
        if (this.reading) {
            return;
        }

        if (this.commonMaze != null && this.commonMaze.getFields() != null) {
            return;
        }

        if (rows <= 0 || cols <= 0) {
            return;
        }

        this.reading = true;
        this.rowCounter = 1;
        this.commonMaze = new Maze(rows, cols);
    }

    public void stopReading() {
        if (this.reading) {
            this.rowCounter++;
            this.reading = false;
        }
    }
}
