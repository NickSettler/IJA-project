package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonMaze;

public class GhostObject extends BaseObject {
    public GhostObject(int row, int col, CommonMaze commonMaze) {
        super(row, col, commonMaze);
    }

    public void die() {
        this.maze.removeObject(this, this.row, this.col);
    }
}
