package ija.ija2022.project.maze;

import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.fields.WallField;
import ija.ija2022.project.objects.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Maze implements CommonMaze {
    private final int rows;
    private final int cols;
    private final CommonField[][] fields;
    private final ArrayList<CommonMazeObject>[][] objects;
    private final List<Pair<Integer, Integer>> updatesFields = new ArrayList<>();
    private List<int[]> pacmanPath = Collections.emptyList();

    public Maze(int rows, int cols) {
        this.rows = rows + 2;
        this.cols = cols + 2;
        this.fields = new CommonField[this.rows][this.cols];
        this.objects = new ArrayList[this.rows][this.cols];

        this.initWalls();
        this.initObjects();
    }

    private void initWalls() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (i == 0 || i == this.rows - 1 || j == 0 || j == this.cols - 1) {
                    this.fields[i][j] = new WallField(i, j);
                }
            }
        }
    }

    private void initObjects() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; ++j) {
                this.objects[i][j] = new ArrayList<>();
            }
        }
    }

    @Override
    public CommonField getField(int row, int col) {
        if (row < 0 || row >= this.rows)
            return null;

        if (col < 0 || col >= this.cols)
            return null;

        return this.fields[row][col];
    }

    @Override
    public int numCols() {
        return this.cols;
    }

    @Override
    public GhostObject[] ghosts() {
        return Arrays.stream(this.objects).flatMap(Arrays::stream)
                .flatMap(List::stream)
                .filter(object -> object instanceof GhostObject)
                .toArray(GhostObject[]::new);
    }

    public KeyObject[] keys() {
        return Arrays.stream(this.objects).flatMap(Arrays::stream)
                .flatMap(List::stream)
                .filter(object -> object instanceof KeyObject)
                .toArray(KeyObject[]::new);
    }

    public HeartObject[] hearts() {
        return Arrays.stream(this.objects).flatMap(Arrays::stream)
                .flatMap(List::stream)
                .filter(object -> object instanceof HeartObject)
                .toArray(HeartObject[]::new);
    }

    public PacmanObject getPacman() {
        return Arrays.stream(this.objects).flatMap(Arrays::stream)
                .flatMap(List::stream)
                .filter(object -> object instanceof PacmanObject)
                .map(object -> (PacmanObject) object)
                .findFirst()
                .orElse(null);
    }

    @Override
    public int numRows() {
        return this.rows;
    }

    @Override
    public void setField(int row, int col, CommonField iField) {
        this.fields[row][col] = iField;
    }

    @Override
    public CommonField[][] getFields() {
        return this.fields;
    }

    @Override
    public ArrayList<CommonMazeObject>[][] getObjects() {
        return this.objects;
    }

    @Override
    public void putObject(CommonMazeObject object, int row, int col) {
        if (object == null)
            return;

        this.objects[row][col].add(object);
        this.updatesFields.add(new Pair<>(row, col));
    }

    public void moveObject(CommonMazeObject object, int row, int col) {
        if (object == null) return;

        for (int i = 0; i < this.objects.length; i++) {
            for (int j = 0; j < this.objects[i].length; j++) {
                if (this.objects[i][j].contains(object)) {
                    this.objects[i][j].remove(object);
                    this.updatesFields.add(new Pair<>(i, j));
                }
            }
        }

        this.objects[row][col].add(object);
        this.updatesFields.add(new Pair<>(row, col));
    }

    @Override
    public void removeObject(CommonMazeObject object, int row, int col) {
        if (object == null) return;

        this.objects[row][col].remove(object);
        this.updatesFields.add(new Pair<>(row, col));
    }

    public void notifyUpdates() {
        this.updatesFields.forEach(pair -> this.fields[pair.getKey()][pair.getValue()].notifyObservers());
        this.updatesFields.clear();
    }

    public List<int[]> getPacmanPath() {
        return pacmanPath;
    }

    public void setPacmanPath(List<int[]> pacmanPath) {
        this.pacmanPath = pacmanPath;

        pacmanPath.forEach(p -> this.updatesFields.add(new Pair<>(p[0], p[1])));
    }

    public boolean isAllKeysCollected() {
        return Arrays.stream(this.objects)
                .anyMatch(row -> Arrays.stream(row)
                        .anyMatch(o -> o.stream()
                                .anyMatch(object -> object instanceof KeyObject)
                        )
                );
    }
}
