package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMazeObject;

public class PathField extends BaseField implements CommonField {
    public PathField(int row, int col) {
        super(row, col);
        this.object = null;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (this.commonMaze.getPacman() != null) {
            if (this.commonMaze.getPacman().getCol() == this.col && this.commonMaze.getPacman().getRow() == this.row) {
                return false;
            }
        }
        return this.object == null;
    }

    @Override
    public CommonMazeObject get() {
        if (this.commonMaze.getPacman() != null) {
            if (this.commonMaze.getPacman().getCol() == this.col && this.commonMaze.getPacman().getRow() == this.row) {
                return this.commonMaze.getPacman();
            }
        }
        return this.object;
    }

    @Override
    public boolean put(CommonMazeObject object) {
        if (object == null) return false;
        if (!this.isEmpty()) return false;
        this.object = object;

        this.notifyObservers();

        return true;
    }

    @Override
    public boolean remove(CommonMazeObject object) {
        if (object == null) return false;

        if (this.isEmpty()) return false;

        this.object = null;

        this.notifyObservers();

        return true;
    }
}
