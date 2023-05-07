package ija.ija2022.project.game;

import ija.ija2022.project.astar.AStarPathFinder;
import ija.ija2022.project.collisions.CollisionController;
import ija.ija2022.project.common.BaseGameViewController;
import ija.ija2022.project.events.EventHandler;
import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.KeyDownEvent;
import ija.ija2022.project.events.events.LivesChangeEvent;
import ija.ija2022.project.events.events.PathFieldMouseClickEvent;
import ija.ija2022.project.events.events.WinEvent;
import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.logger.LOGGER_MODE;
import ija.ija2022.project.logger.LoggerController;
import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.maze.MazePresenter;
import ija.ija2022.project.maze.configure.MazeConfigure;
import ija.ija2022.project.objects.GhostObject;
import ija.ija2022.project.objects.PacmanObject;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.ui.controllers.KeyboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameController extends BaseGameViewController {
    private MazeConfigure mazeConfigure;
    private CommonMaze maze;
    private int unfreezeTicks;
    private MazePresenter presenter;
    private GameView view;
    private CollisionController collisionController;
    private LoggerController loggerController;

    public GameController(GAME_MODE mode, String filePath) {
        super(mode);

        this.loggerController = new LoggerController(LOGGER_MODE.WRITE);

        this.mazeConfigure = new MazeConfigure(filePath, true);
        this.maze = this.mazeConfigure.createMaze();
        this.unfreezeTicks = this.maze.ghosts().length * this.settingsController.getFreezeSteps();

        this.collisionController = new CollisionController(this.maze);

        this.presenter = new MazePresenter(this.maze);
        this.view = new GameView(this);

        this.view.setVisible(true);

        if (this.mode == GAME_MODE.CONTINUOUS)
            this.start();

        EventManager.getInstance().fireEvent(new LivesChangeEvent(this.maze.getPacman().getLives()));
    }

    @EventHandler
    private void handleKeyDownEvent(KeyDownEvent event) {
        if (this.mode == GAME_MODE.STEP_BY_STEP)
            this.tick();
        else if (!this.isRunning.get() && !this.isFinished.get())
            this.start();
    }

    @EventHandler
    private void handlePathMouseClickEvent(PathFieldMouseClickEvent event) {
        CommonField field = event.getField();

        if (field == null) return;

        AStarPathFinder pathFinder = new AStarPathFinder(this.maze);
        List<int[]> path = pathFinder.findPath(
                this.maze.getPacman().getRow(),
                this.maze.getPacman().getCol(),
                field.getRow(),
                field.getCol()
        );

        if (path == null || path.isEmpty()) return;

        this.maze.setPacmanPath(path);
    }

    @EventHandler
    private void handleWinEvent(WinEvent e) {
        this.finish();
    }

    protected void update() {
        for (GhostObject ghost : this.maze.ghosts()) {
            if (!ghost.isFrozen()) {
                ghost.generateDirection();
                ghost.move();
            } else {
                this.unfreezeTicks--;
            }

            this.loggerController.addItem(ghost);
        }

        if (this.unfreezeTicks <= 0 && Arrays.stream(this.maze.ghosts()).anyMatch(GhostObject::isFrozen))
            this.maze.unfreezeGhosts();

        PacmanObject pacman = this.maze.getPacman();

        if (this.maze.getPacmanPath().size() == 0) {
            Map<Integer, Boolean> keys = KeyboardController.getInstance().getKeys();

            if (keys.getOrDefault(KeyEvent.VK_W, false))
                pacman.setDirection(CommonField.Direction.U);
            else if (keys.getOrDefault(KeyEvent.VK_S, false))
                pacman.setDirection(CommonField.Direction.D);
            else if (keys.getOrDefault(KeyEvent.VK_A, false))
                pacman.setDirection(CommonField.Direction.L);
            else if (keys.getOrDefault(KeyEvent.VK_D, false))
                pacman.setDirection(CommonField.Direction.R);

            if (!this.maze.getField(pacman.getRow() + pacman.getDirection().deltaRow(), pacman.getCol() + pacman.getDirection().deltaCol()).canMove())
                pacman.setDirection(CommonField.Direction.N);

            keys.clear();

            pacman.move();
        } else {
            pacman.move(this.maze.getPacmanPath().get(0)[0], this.maze.getPacmanPath().get(0)[1]);
            this.maze.getPacmanPath().remove(0);

            if (this.maze.getPacmanPath().isEmpty())
                pacman.setDirection(CommonField.Direction.N);
        }

        this.loggerController.addItem(pacman);
        this.loggerController.nextEntry();

        this.collisionController.detectCollisions();
        this.collisionController.handleCollisions();
    }

    protected void render() {
        this.maze.notifyUpdates();
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void handleWindowClose(Window window) {
        this.stop();

        this.showSaveModal(window, "Save Game", "Do you really want to save the game?");
    }

    private void showSaveModal(Window window, String title, String message) {
        String[] options = {"Yes! Please.", "No! Not now.", "Cancel"};
        int result = JOptionPane.showOptionDialog(
                window,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (result == JOptionPane.YES_OPTION) {
            JFileChooser fileChooser = new JFileChooser("data/");
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(window);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                this.loggerController.close(fileToSave.getAbsolutePath(), this.mazeConfigure.getMazeText());
                this.destroy();
            }

            if (userSelection == JFileChooser.CANCEL_OPTION) {
                this.showSaveModal(window, title, message);
            }
        }

        if (result == JOptionPane.NO_OPTION)
            this.destroy();

        if (result == JOptionPane.CANCEL_OPTION && !this.isFinished.get())
            this.start();
    }

    @Override
    public void destroy() {
        super.destroy();

        EventManager.getInstance().removeEventListener(this);

        this.view.dispose();
        this.settingsController = null;
        this.loggerController = null;
        this.collisionController = null;
        this.maze = null;
        this.mazeConfigure = null;
        this.presenter = null;
        this.view = null;
    }

    @Override
    public JPanel getMazeView() {
        return this.presenter;
    }
}