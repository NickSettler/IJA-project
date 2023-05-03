package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.common.CommonMazeObject;
import ija.ija2022.project.tool.common.Observable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GhostObject implements CommonMazeObject {
    private int row;

    private int col;

    private final CommonMaze commonMaze;

    private final Set<Observable.Observer> observers = new HashSet<>();

    public GhostObject(int row, int col, CommonMaze commonMaze) {
        this.row = row;
        this.col = col;
        this.commonMaze = commonMaze;
    }

    @Override
    public boolean canMove(CommonField.Direction dir) {
        CommonField nextField = this.commonMaze.getField(this.row + dir.deltaRow(), this.col + dir.deltaCol());

        return nextField != null && nextField.canMove();
    }

    @Override
    public boolean move(CommonField.Direction dir) {
        boolean canMove = this.canMove(dir);

        if (!canMove) return false;

        int nextRow = this.row + dir.deltaRow();
        int nextCol = this.col + dir.deltaCol();

        this.commonMaze.moveObject(this, nextRow, nextCol);

        this.row = nextRow;
        this.col = nextCol;

        PacmanObject pacman = this.commonMaze.getPacman();
        if (pacman != null) {
            if (pacman.getRow() == this.row && pacman.getCol() == this.col) {
                pacman.decrLives();
            }
        }

        return true;
    }

    public void makeMove() {
        CommonField.Direction[] directions = CommonField.Direction.values();

        for (CommonField.Direction direction : directions) {
            if (!this.canMove(direction)) {
                directions = Arrays.stream(directions).filter(dir -> dir != direction).toArray(CommonField.Direction[]::new);
            }
        }

        if (directions.length == 0) return;

        int randomIndex = (int) (Math.random() * directions.length);
        CommonField.Direction randomDirection = directions[randomIndex];

        this.move(randomDirection);
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
    public CommonField getField() {
        return this.commonMaze.getField(this.row, this.col);
    }

    @Override
    public int getLives() {
        return 0;
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
