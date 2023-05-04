package ija.ija2022.project.game;

import ija.ija2022.project.Main;
import ija.ija2022.project.events.EventHandler;
import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.KeyDownEvent;
import ija.ija2022.project.game.collision.CollisionController;
import ija.ija2022.project.game.configure.MazeConfigure;
import ija.ija2022.project.game.logger.LOGGER_MODE;
import ija.ija2022.project.game.logger.LogEntry;
import ija.ija2022.project.game.logger.LogItem;
import ija.ija2022.project.game.logger.LoggerController;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.MazePresenter;
import ija.ija2022.project.tool.common.CommonMaze;

import javax.swing.*;
import java.util.Arrays;

public class ReplayController {
    private final CommonMaze maze;
    private final GAME_MODE mode;

    private final MazePresenter presenter;
    private final CollisionController collisionController;
    private final LoggerController logger;

    public ReplayController(GAME_MODE mode, String filePath) {
        this.logger = new LoggerController(LOGGER_MODE.READ, filePath);

        MazeConfigure mazeConfigure = new MazeConfigure(this.logger.getMapText());

        this.maze = mazeConfigure.createMaze();
        this.mode = mode;
        this.presenter = new MazePresenter(this.maze);

        this.collisionController = new CollisionController(this.maze);

        EventManager.getInstance().addEventListener(this);
    }

    @EventHandler
    private void handleKeyDownEvent(KeyDownEvent e) {
        if (this.mode == GAME_MODE.STEP_BY_STEP)
            this.tick();
    }

    private void update() {
        this.processBatchCommands(this.logger.currentEntry());
        this.logger.nextEntry();

        this.collisionController.detectCollisions();
        this.collisionController.handleCollisions();
    }

    private void render() {
        this.maze.notifyUpdates();
    }

    private void tick() {
        this.update();
        this.render();

        if (this.mode == GAME_MODE.CONTINUOUS && this.logger.currentEntry() != null) {
            Main.sleep(250);
            this.tick();
        }
    }

    private void processCommand(LogItem command) {
        switch (command.character()) {
            case PACMAN -> this.maze.getPacman().move(command.direction());

            case GHOST -> Arrays.stream(this.maze.ghosts())
                    .filter(ghost -> ghost.getRow() == command.from().getKey() && ghost.getCol() == command.from().getValue())
                    .findFirst()
                    .ifPresent(ghost -> ghost.move(command.direction()));
        }
    }

    private void processBatchCommands(LogEntry commandsEntry) {
        for (LogItem command : commandsEntry.items()) {
            this.processCommand(command);
        }
    }

    public void start() {
        this.presenter.open();

        if (this.mode == GAME_MODE.CONTINUOUS)
            this.tick();
    }

    public JPanel getFrame() {
        return (JPanel) this.presenter.getFrame().getContentPane();
    }
}
