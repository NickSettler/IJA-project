package ija.ija2022.project.game;

import ija.ija2022.project.events.EventHandler;
import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.KeyDownEvent;
import ija.ija2022.project.game.collision.CollisionController;
import ija.ija2022.project.game.configure.MazeConfigure;
import ija.ija2022.project.game.logger.LOGGER_MODE;
import ija.ija2022.project.game.logger.LoggerController;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.MazePresenter;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.ui.ReplayView;

import javax.swing.*;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class ReplayController extends BaseMazeController {
    private final CommonMaze maze;
    private REPLAY_DIRECTION replayDirection = REPLAY_DIRECTION.FORWARD;
    private final MazePresenter presenter;
    private final ReplayView view;
    private final CollisionController collisionController;
    private final LoggerController logger;
    private final CommandProcessor commandProcessor;

    public ReplayController(GAME_MODE mode, String filePath) {
        super(mode);

        this.logger = new LoggerController(LOGGER_MODE.READ, filePath);

        MazeConfigure mazeConfigure = new MazeConfigure(this.logger.getMapText());

        this.maze = mazeConfigure.createMaze();
        this.presenter = new MazePresenter(this.maze);
        this.view = new ReplayView(this);

        this.collisionController = new CollisionController(this.maze);
        this.commandProcessor = new CommandProcessor(this.maze, this.logger);

        this.view.setVisible(true);
    }

    @EventHandler
    private void handleKeyDownEvent(KeyDownEvent e) {
        if (e.getKeyCode() != VK_LEFT && e.getKeyCode() != VK_RIGHT)
            return;

        if (this.mode == GAME_MODE.STEP_BY_STEP) {
            if (e.getKeyCode() == VK_LEFT) {
                if (this.replayDirection == REPLAY_DIRECTION.FORWARD)
                    this.logger.previousEntry();
                this.replayDirection = REPLAY_DIRECTION.BACKWARD;
            } else if (e.getKeyCode() == VK_RIGHT) {
                if (this.replayDirection == REPLAY_DIRECTION.BACKWARD)
                    this.logger.nextEntry();
                this.replayDirection = REPLAY_DIRECTION.FORWARD;
            }

            System.out.println("Going " + (this.replayDirection == REPLAY_DIRECTION.FORWARD ? "forward" : "backward"));

            this.tick();
        }
    }

    protected void update() {
        super.updateTimeToSleep(250 + view.getTimeChange());

        System.out.println("Processing entry: " + this.logger.getIndex() + ". Reverse: " + (this.replayDirection == REPLAY_DIRECTION.BACKWARD));

        this.commandProcessor.processEntry(
                this.logger.getEntry(this.logger.getIndex()),
                replayDirection == REPLAY_DIRECTION.BACKWARD
        );

        if (replayDirection == REPLAY_DIRECTION.FORWARD)
            this.logger.nextEntry();
        else
            this.logger.previousEntry();

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

    @Override
    public void destroy() {
        super.destroy();

        EventManager.getInstance().removeEventListener(this);

        this.view.dispose();
        this.logger.close();
    }

    public JPanel getMazeView() {
        return this.presenter;
    }

}
