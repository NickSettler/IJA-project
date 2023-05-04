package ija.ija2022.project.game;

import ija.ija2022.project.events.EventHandler;
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
import ija.ija2022.project.ui.ReplayView;

import javax.swing.*;
import java.util.Arrays;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class ReplayController extends BaseMazeController {
    private final CommonMaze maze;
    private REPLAY_DIRECTION replayDirection = REPLAY_DIRECTION.FORWARD;
    private final MazePresenter presenter;
    private final ReplayView view;
    private final CollisionController collisionController;
    private final LoggerController logger;

    public ReplayController(GAME_MODE mode, String filePath) {
        super(mode);

        this.logger = new LoggerController(LOGGER_MODE.READ, filePath);

        MazeConfigure mazeConfigure = new MazeConfigure(this.logger.getMapText());

        this.maze = mazeConfigure.createMaze();
        this.presenter = new MazePresenter(this.maze);
        this.view = new ReplayView(this);

        this.collisionController = new CollisionController(this.maze);

        this.view.setVisible(true);
    }

    @EventHandler
    private void handleKeyDownEvent(KeyDownEvent e) {
        if (e.getKeyCode() != VK_LEFT && e.getKeyCode() != VK_RIGHT)
            return;

        if (this.mode == GAME_MODE.STEP_BY_STEP) {
            this.replayDirection = e.getKeyCode() == VK_LEFT ? REPLAY_DIRECTION.BACKWARD : REPLAY_DIRECTION.FORWARD;

            this.tick();
        }
    }

    protected void update() {
        if (replayDirection == REPLAY_DIRECTION.BACKWARD)
            this.logger.previousEntry();

        this.processBatchCommands(this.logger.currentEntry());

        if (replayDirection == REPLAY_DIRECTION.FORWARD)
            this.logger.nextEntry();

        this.collisionController.detectCollisions();
        this.collisionController.handleCollisions();
    }

    protected void render() {
        this.maze.notifyUpdates();
    }

    protected void runCheck() {
        if (this.logger.currentEntry() == null)
            this.stop();
    }

    private void processCommand(LogItem command) {
        boolean reverse = this.replayDirection == REPLAY_DIRECTION.BACKWARD;

        switch (command.character()) {
            case PACMAN -> this.maze.getPacman().move(command.to(reverse).getKey(), command.to(reverse).getValue());

            case GHOST -> Arrays.stream(this.maze.ghosts())
                    .filter(ghost -> ghost.getRow() == command.from(reverse).getKey() && ghost.getCol() == command.from(reverse).getValue())
                    .findFirst()
                    .ifPresent(ghost -> ghost.move(command.to(reverse).getKey(), command.to(reverse).getValue()));
        }
    }

    private void processBatchCommands(LogEntry commandsEntry) {
        if (commandsEntry == null) return;

        for (LogItem command : commandsEntry.items()) {
            this.processCommand(command);
        }
    }

    public JPanel getMazeView() {
        return this.presenter;
    }
}
