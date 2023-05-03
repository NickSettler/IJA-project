package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.AbstractObservableField;
import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.Objects;

public class BaseField extends AbstractObservableField {
    protected int row;

    protected int col;

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
        ArrayList<CommonMazeObject> objects = this.commonMaze.getObjects()[this.row][this.col];

        return objects.contains(commonMazeObject);
    }

    @Override
    public ArrayList<CommonMazeObject> get() {
        return this.commonMaze.getObjects()[this.row][this.col];
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
    public void put(CommonMazeObject object) {
        if (object == null) return;

        this.commonMaze.getObjects()[this.row][this.col].add(object);
        this.commonMaze.getFields()[this.row][this.col].notifyObservers();
    }

    @Override
    public void remove(CommonMazeObject object) {
        ArrayList<CommonMazeObject> objects = this.commonMaze.getObjects()[this.row][this.col];

        if (objects.isEmpty()) return;

        objects.remove(object);
        this.commonMaze.getFields()[this.row][this.col].notifyObservers();
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
