package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonMaze;

import java.util.Objects;

public class PacmanObject extends BaseObject {
    private int lives;

    public PacmanObject(int row, int col, CommonMaze commonMaze) {
        super(row, col, commonMaze);

        this.lives = 3;
    }

    @Override
    public boolean isPacman() {
        return true;
    }

    public int getLives() {
        return this.lives;
    }

    public void decrLives() {
        this.lives -= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacmanObject that = (PacmanObject) o;

        if (getRow() != that.getRow()) return false;
        if (getCol() != that.getCol()) return false;
        if (lives != that.lives) return false;
        return Objects.equals(maze, that.maze);
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getCol();
        result = 31 * result + lives;
        result = 31 * result + (maze != null ? maze.hashCode() : 0);
        return result;
    }
}
