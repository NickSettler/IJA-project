package ija.ija2022.project.game;

import ija.ija2022.project.Main;
import ija.ija2022.project.events.EVENTS;
import ija.ija2022.project.events.EventsSystem;
import ija.ija2022.project.game.collision.CollisionController;
import ija.ija2022.project.game.logger.LOGGER_MODE;
import ija.ija2022.project.game.logger.LoggerController;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.MazePresenter;
import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;

import java.awt.event.KeyEvent;
import java.util.Map;

public class GameController {
    private final CommonMaze maze;
    private final GAME_MODE mode;

    private final CollisionController collisionController;

    private final LoggerController loggerController;

    public GameController(CommonMaze maze, GAME_MODE mode) {
        this.maze = maze;
        this.mode = mode;

        this.collisionController = new CollisionController(this.maze);
        this.loggerController = new LoggerController(LOGGER_MODE.WRITE, "data/history03.json");

        if (this.mode == GAME_MODE.STEP_BY_STEP)
            EventsSystem.getInstance().on(EVENTS.KEYDOWN, (Object obj) -> this.tick());

        EventsSystem.getInstance().on(EVENTS.CLOSE_PRESENTER, (Object obj) -> this.loggerController.close());
    }

    private void update() {
        for (GhostObject ghost : this.maze.ghosts()) {
            ghost.generateDirection();
            ghost.move();
            this.loggerController.addItem(ghost);
        }

        Map<Integer, Boolean> keys = KeyboardController.getInstance().getKeys();

        PacmanObject pacman = this.maze.getPacman();

        if (keys.getOrDefault(KeyEvent.VK_W, false))
            pacman.setDirection(CommonField.Direction.U);
        else if (keys.getOrDefault(KeyEvent.VK_S, false))
            pacman.setDirection(CommonField.Direction.D);
        else if (keys.getOrDefault(KeyEvent.VK_A, false))
            pacman.setDirection(CommonField.Direction.L);
        else if (keys.getOrDefault(KeyEvent.VK_D, false))
            pacman.setDirection(CommonField.Direction.R);

        keys.clear();

        pacman.move();

        this.loggerController.addItem(pacman);
        this.loggerController.nextEntry();

        this.collisionController.detectCollisions();
        this.collisionController.handleCollisions();
    }

    private void render() {
        this.maze.notifyUpdates();
    }

    private void tick() {
        this.update();
        this.render();

        if (this.mode == GAME_MODE.CONTINUOUS) {
            Main.sleep(250);
            this.tick();
        }
    }

    public void start() {
        MazePresenter presenter = new MazePresenter(maze);
        presenter.open();

        if (this.mode == GAME_MODE.CONTINUOUS)
            this.tick();
    }
}