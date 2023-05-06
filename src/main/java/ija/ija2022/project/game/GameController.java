package ija.ija2022.project.game;

import ija.ija2022.project.astar.AStarPathFinder;
import ija.ija2022.project.events.EventHandler;
import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.KeyDownEvent;
import ija.ija2022.project.events.events.MouseClickedEvent;
import ija.ija2022.project.game.collision.CollisionController;
import ija.ija2022.project.game.configure.MazeConfigure;
import ija.ija2022.project.game.logger.LOGGER_MODE;
import ija.ija2022.project.game.logger.LoggerController;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.MazePresenter;
import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.view.FieldView;
import ija.ija2022.project.ui.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameController extends BaseMazeController {
    private final MazeConfigure mazeConfigure;
    private final CommonMaze maze;
    private final MazePresenter presenter;
    private final GameView view;
    private List<CommonField.Direction> pacmanPath = Collections.emptyList();
    private final CollisionController collisionController;
    private final LoggerController loggerController;

    public GameController(GAME_MODE mode, String filePath) {
        super(mode);

        this.loggerController = new LoggerController(LOGGER_MODE.WRITE);

        this.mazeConfigure = new MazeConfigure(filePath, true);
        this.maze = this.mazeConfigure.createMaze();
        this.presenter = new MazePresenter(this.maze);
        this.view = new GameView(this);

        this.collisionController = new CollisionController(this.maze);

        this.view.setVisible(true);

        if (this.mode == GAME_MODE.CONTINUOUS)
            this.start();
    }

    @EventHandler
    private void handleKeyDownEvent(KeyDownEvent event) {
        if (this.mode == GAME_MODE.STEP_BY_STEP)
            this.tick();
        else if (!this.isRunning.get())
            this.start();
    }

    @EventHandler
    private void handleMouseClickEvent(MouseClickedEvent event) {
        FieldView field = (FieldView) SwingUtilities.getDeepestComponentAt(this.view, event.getX(), event.getY());

        if (field == null || field.getField() == null)
            return;

        AStarPathFinder pathFinder = new AStarPathFinder(this.maze);
        List<CommonField.Direction> path = pathFinder.findPath(
                this.maze.getPacman().getRow(),
                this.maze.getPacman().getCol(),
                field.getField().getRow(),
                field.getField().getCol()
        );

        if (path == null || path.isEmpty()) return;

        this.pacmanPath = path;
    }

    protected void update() {
        for (GhostObject ghost : this.maze.ghosts()) {
            ghost.generateDirection();
            ghost.move();
            this.loggerController.addItem(ghost);
        }

        PacmanObject pacman = this.maze.getPacman();

        if (this.pacmanPath.isEmpty()) {
            Map<Integer, Boolean> keys = KeyboardController.getInstance().getKeys();

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
        } else {
            pacman.setDirection(this.pacmanPath.get(0));
            pacman.move();
            this.pacmanPath.remove(0);

            if (this.pacmanPath.isEmpty())
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

    public void handleWindowClose(Window window) {
        if (this.loggerController.getIndex() == 0)
            return;

        this.stop();

        String[] options = {"Yes! Please.", "No! Not now."};
        int result = JOptionPane.showOptionDialog(
                window,
                "Do you want to save the game?",
                "Save game",
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
            }
        }

        System.out.println("Here");
        this.destroy();
    }

    @Override
    public void destroy() {
        super.destroy();

        EventManager.getInstance().removeEventListener(this);

        this.view.dispose();
    }

    @Override
    public JPanel getMazeView() {
        return this.presenter;
    }
}