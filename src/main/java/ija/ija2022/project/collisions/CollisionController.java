package ija.ija2022.project.collisions;

import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.WinEvent;
import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.objects.*;

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
            if (collides(pacman, ghost))
                this.collisions.add(new Collision(pacman, ghost, (pair) -> {
                    PacmanObject p = (PacmanObject) pair.getKey();
                    GhostObject g = (GhostObject) pair.getValue();

                    if (!g.isFrozen())
                        p.decrLives();
                    g.die();
                }));
        }

        for (KeyObject key : this.maze.keys()) {
            if (this.collidesBasic(pacman, key))
                this.collisions.add(new Collision(pacman, key, (pair) -> {
                    KeyObject k = (KeyObject) pair.getValue();

                    k.collect();
                }));
        }

        for (ClockObject clock : this.maze.clocks()) {
            if (this.collidesBasic(pacman, clock))
                this.collisions.add(new Collision(pacman, clock, (pair) -> {
                    ClockObject c = (ClockObject) pair.getValue();

                    c.collect();
                }));
        }

        for (HeartObject heart : this.maze.hearts()) {
            if (this.collidesBasic(pacman, heart))
                this.collisions.add(new Collision(pacman, heart, (pair) -> {
                    PacmanObject p = (PacmanObject) pair.getKey();
                    HeartObject h = (HeartObject) pair.getValue();

                    p.incrLives();
                    h.collect();
                }));
        }

        if (this.collidesBasic(pacman, this.maze.target())) {
            EventManager.getInstance().fireEvent(new WinEvent());
        }
    }

    private boolean collidesBasic(PacmanObject pacman, CommonMazeObject object) {
        return pacman.getRow() == object.getRow() && pacman.getCol() == object.getCol();
    }

    private boolean collides(PacmanObject pacman, GhostObject ghost) {
        int pacmanPrevRow = pacman.getRow() - pacman.getDirection().deltaRow();
        int pacmanPrevCol = pacman.getCol() - pacman.getDirection().deltaCol();
        int ghostPrevRow = ghost.getRow() - ghost.getDirection().deltaRow();
        int ghostPrevCol = ghost.getCol() - ghost.getDirection().deltaCol();
        boolean collidesByPrevPosition = pacmanPrevRow == ghostPrevRow && pacmanPrevCol == ghostPrevCol;
        boolean collidesByCrossPositions = (pacman.getRow() == ghostPrevRow && pacman.getCol() == ghostPrevCol)
                || (pacmanPrevRow == ghost.getRow() && pacmanPrevCol == ghost.getCol());


        return this.collidesBasic(pacman, ghost) || collidesByPrevPosition || collidesByCrossPositions;
    }

    public void handleCollisions() {
        for (Collision collision : this.collisions) collision.handle();

        this.collisions.clear();
    }
}
