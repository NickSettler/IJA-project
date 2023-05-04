package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.common.CommonMazeObject;
import ija.ija2022.project.tool.common.Observable;

import java.util.HashSet;
import java.util.Set;

public class BaseObject implements CommonMazeObject {
    protected int row;

    protected int col;

    protected final CommonMaze maze;

    private final Set<Observer> observers = new HashSet<>();

    BaseObject(int row, int col, CommonMaze maze) {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }

    @Override
    public boolean canMove(CommonField.Direction direction) {
        CommonField nextField = this.maze.getField(
                this.row + direction.deltaRow(),
                this.col + direction.deltaCol()
        );

        return nextField != null && nextField.canMove();
    }

    @Override
    public void move(CommonField.Direction direction) {
        boolean canMove = this.canMove(direction);

        if (!canMove) return;

        int nextRow = this.row + direction.deltaRow();
        int nextCol = this.col + direction.deltaCol();

        this.row = nextRow;
        this.col = nextCol;

        this.maze.moveObject(this, nextRow, nextCol);
    }

    @Override
    public boolean isPacman() {
        return CommonMazeObject.super.isPacman();
    }

    @Override
    public CommonField getField() {
        return this.maze.getField(this.row, this.col);
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    @Override
    public void addObserver(Observable.Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observable.Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(observer -> observer.update(this));
    }
}
