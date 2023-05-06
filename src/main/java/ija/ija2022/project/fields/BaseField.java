package ija.ija2022.project.fields;

import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.objects.CommonMazeObject;

import java.util.ArrayList;
import java.util.Objects;

public class BaseField extends AbstractObservableField {
    protected final int row;

    protected final int col;

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
    public ArrayList<CommonMazeObject> get() {
        return this.commonMaze.getObjects()[this.row][this.col];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean isEmpty() {
        return this.commonMaze.getObjects()[this.row][this.col].isEmpty();
    }

    @Override
    public CommonField nextField(Direction dirs) {
        return this.commonMaze.getField(this.row + dirs.deltaRow(), this.col + dirs.deltaCol());
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
        return Objects.hash(row, col, commonMaze);
    }
}
