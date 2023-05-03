package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.*;

import java.util.Objects;

public class BaseField extends AbstractObservableField {
    protected int row;

    protected int col;

    protected CommonMazeObject object;

    protected CommonMaze commonMaze;

    public BaseField(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean contains(CommonMazeObject commonMazeObject) {
        return this.object == commonMazeObject;
    }

    @Override
    public CommonMazeObject get() {
        return this.object;
    }

    @Override
    public boolean isEmpty() {
        return this.object == null;
    }

    @Override
    public CommonField nextField(Direction dirs) {
        return this.commonMaze.getField(this.row + dirs.deltaRow(), this.col + dirs.deltaCol());
    }

    @Override
    public boolean put(CommonMazeObject object) {
        if (object == null) return false;

        this.object = object;
        return true;
    }

    @Override
    public boolean remove(CommonMazeObject object) {
        if (object == null || this.object != object) return false;

        this.object = null;
        return true;
    }

    @Override
    public void setMaze(CommonMaze commonMaze) {
        this.commonMaze = commonMaze;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseField baseField = (BaseField) o;

        if (row != baseField.row) return false;
        return col == baseField.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, commonMaze, object);
    }
}
