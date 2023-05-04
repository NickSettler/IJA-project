package ija.ija2022.project.game;

import ija.ija2022.project.Main;
import ija.ija2022.project.events.EVENTS;
import ija.ija2022.project.events.EventsSystem;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.MazePresenter;
import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;

import java.awt.event.KeyEvent;

public class GameController {
    private CommonMaze maze;
    private GAME_MODE mode;
    private MazePresenter presenter;

    public GameController(CommonMaze maze, GAME_MODE mode) {
        this.maze = maze;
        this.mode = mode;

        PacmanObject pacman = this.maze.getPacman();

        EventsSystem.getInstance().on(EVENTS.KEYDOWN, (Object codes) -> {
            Integer code = (Integer) ((Object[]) codes)[0];

            switch (code) {
                case KeyEvent.VK_W -> pacman.setDirection(CommonField.Direction.U);
                case KeyEvent.VK_S -> pacman.setDirection(CommonField.Direction.D);
                case KeyEvent.VK_A -> pacman.setDirection(CommonField.Direction.L);
                case KeyEvent.VK_D -> pacman.setDirection(CommonField.Direction.R);
            }

            if (this.mode == GAME_MODE.STEP_BY_STEP)
                this.tick();
        });
    }

    private void tick() {
        this.moveGhosts();

//        if (this.mode == GAME_MODE.CONTINUOUS)
        this.movePacman();

        this.maze.notifyUpdates();

        if (this.mode == GAME_MODE.CONTINUOUS) {
            Main.sleep(250);
            this.tick();
        }
    }

    private void moveGhosts() {
        for (GhostObject ghost : this.maze.ghosts()) {
            ghost.generateDirection();
            ghost.move();
        }
    }

    private void movePacman() {
        PacmanObject pacman = this.maze.getPacman();
        pacman.move();
    }

    public void start() {
        this.presenter = new MazePresenter(maze);
        this.presenter.open();

        if (this.mode == GAME_MODE.CONTINUOUS)
            this.tick();
    }
}