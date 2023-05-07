package ija.ija2022.project.collisions;

import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.objects.GhostObject;
import ija.ija2022.project.objects.KeyObject;
import ija.ija2022.project.objects.PacmanObject;

import java.util.ArrayList;
import java.util.List;

public class CollisionController {
    private final CommonMaze maze;

    private final List<Collision> collisions = new ArrayList<>();

    public CollisionController(CommonMaze maze) {
        this.maze = maze;
    }

    public void detectCollisions() {
        PacmanObject pacman = this.maze.getPacman();

        for (GhostObject ghost : this.maze.ghosts()) {
            if (collides(pacman, ghost)) {
                this.collisions.add(new Collision(pacman, ghost, (pair) -> {
                    PacmanObject p = (PacmanObject) pair.getKey();
                    GhostObject g = (GhostObject) pair.getValue();

                    p.decrLives();
                    g.die();
                }));
            }
        }

        for (KeyObject key : this.maze.keys()) {
            if (pacman.getRow() == key.getRow() && pacman.getCol() == key.getCol()) {
                this.collisions.add(new Collision(pacman, key, (pair) -> {
                    KeyObject k = (KeyObject) pair.getValue();

                    k.collect();
                }));
            }
        }
    }

    public boolean collides(PacmanObject pacman, GhostObject ghost) {
        int pacmanPrevRow = pacman.getRow() - pacman.getDirection().deltaRow();
        int pacmanPrevCol = pacman.getCol() - pacman.getDirection().deltaCol();
        int ghostPrevRow = ghost.getRow() - ghost.getDirection().deltaRow();
        int ghostPrevCol = ghost.getCol() - ghost.getDirection().deltaCol();

        boolean collidesByCurrentPosition = pacman.getRow() == ghost.getRow() && pacman.getCol() == ghost.getCol();
        boolean collidesByPrevPosition = pacmanPrevRow == ghostPrevRow && pacmanPrevCol == ghostPrevCol;
        boolean collidesByCrossPositions = (pacman.getRow() == ghostPrevRow && pacman.getCol() == ghostPrevCol)
                || (pacmanPrevRow == ghost.getRow() && pacmanPrevCol == ghost.getCol());


        return collidesByCurrentPosition || collidesByPrevPosition || collidesByCrossPositions;
    }

    public void handleCollisions() {
        for (Collision collision : this.collisions) collision.handle();

        this.collisions.clear();
    }
}
