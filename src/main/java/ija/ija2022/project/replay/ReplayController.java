package ija.ija2022.project.replay;

import ija.ija2022.project.collisions.CollisionController;
import ija.ija2022.project.commands.CommandProcessor;
import ija.ija2022.project.common.BaseGameViewController;
import ija.ija2022.project.events.EventHandler;
import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.KeyDownEvent;
import ija.ija2022.project.events.events.LivesChangeEvent;
import ija.ija2022.project.logger.LOGGER_MODE;
import ija.ija2022.project.logger.LogEntry;
import ija.ija2022.project.logger.LoggerController;
import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.maze.MazePresenter;
import ija.ija2022.project.maze.configure.MazeConfigure;
import ija.ija2022.project.settings.GAME_MODE;

import javax.swing.*;
import java.util.List;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class ReplayController extends BaseGameViewController {
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

        EventManager.getInstance().fireEvent(new LivesChangeEvent(this.maze.getPacman().getLives()));
    }

    @EventHandler
    private void handleKeyDownEvent(KeyDownEvent e) {
        if (e.getKeyCode() != VK_LEFT && e.getKeyCode() != VK_RIGHT)
            return;

        if (this.mode == GAME_MODE.STEP_BY_STEP) {
            if (e.getKeyCode() == VK_LEFT) this.preparePreviousStep();
            else if (e.getKeyCode() == VK_RIGHT) this.prepareNextStep();

            this.tick();
        }
    }

    private void preparePreviousStep() {
        if (this.replayDirection == REPLAY_DIRECTION.FORWARD)
            this.logger.previousEntry();
        this.replayDirection = REPLAY_DIRECTION.BACKWARD;
    }

    private void prepareNextStep() {
        if (this.replayDirection == REPLAY_DIRECTION.BACKWARD)
            this.logger.nextEntry();
        this.replayDirection = REPLAY_DIRECTION.FORWARD;
    }

    public void previousStep() {
        this.preparePreviousStep();
        this.tick();
    }

    public void nextStep() {
        this.prepareNextStep();
        this.tick();
    }

    public void jumpToStep(int step) {
        boolean reverse = this.logger.getIndex() > step;

        List<LogEntry> entries = this.logger.getEntries(reverse ? step : this.logger.getIndex(), reverse ? this.logger.getIndex() : step);
        this.commandProcessor.processEntries(entries, reverse);
        this.logger.setIndex(step);

        this.collisionController.detectCollisions();
        this.collisionController.handleCollisions();

        this.render();
    }

    protected void update() {
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
