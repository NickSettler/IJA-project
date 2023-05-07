package ija.ija2022.project.objects;

import ija.ija2022.project.maze.CommonMaze;

public class ClockObject extends BaseObject {
    public ClockObject(int row, int col, CommonMaze commonMaze) {
        super(row, col, commonMaze);
    }

    public void collect() {
        this.maze.removeObject(this, this.row, this.col);
    }
}
