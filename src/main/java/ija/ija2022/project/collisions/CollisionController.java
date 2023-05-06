package ija.ija2022.project.collisions;

import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.objects.GhostObject;
import ija.ija2022.project.objects.PacmanObject;

import java.util.ArrayList;
import java.util.List;

public class CollisionController {
    private CommonMaze maze;

    private List<Collision> collisions = new ArrayList<>();

    public CollisionController(CommonMaze maze) {
        this.maze = maze;
    }

    public void detectCollisions() {
        PacmanObject pacman = this.maze.getPacman();

        for (GhostObject ghost : this.maze.ghosts()) {
            if (pacman.getRow() == ghost.getRow() && pacman.getCol() == ghost.getCol()) {
                this.collisions.add(new Collision(pacman, ghost, (pair) -> {
                    PacmanObject p = (PacmanObject) pair.getKey();
                    GhostObject g = (GhostObject) pair.getValue();

                    p.decrLives();
                    g.die();
                }));
            }
        }
    }

    public void handleCollisions() {
        for (Collision collision : this.collisions) {
            collision.handle();
        }

        this.collisions.clear();
    }
}
