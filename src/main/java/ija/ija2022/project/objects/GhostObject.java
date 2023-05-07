/**
 * Represents a ghost object in the maze.
 * Ghost object is an object that can move through the maze.
 * It is controlled by the computer.
 * When the player collides with the ghost, the player loses a life and the ghost is removed from the maze.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.objects;

import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.maze.CommonMaze;

public class GhostObject extends BaseObject {
    private boolean frozen = false;

    public GhostObject(int row, int col, CommonMaze commonMaze) {
        super(row, col, commonMaze);
    }

    public void die() {
        this.maze.removeObject(this, this.row, this.col);
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
        this.direction = CommonField.Direction.N;
    }
}
