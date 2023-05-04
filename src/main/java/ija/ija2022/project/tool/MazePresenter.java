package ija.ija2022.project.tool;

import ija.ija2022.project.game.KeyboardController;
import ija.ija2022.project.game.WindowController;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.view.FieldView;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazePresenter {
    private final CommonMaze maze;

    public MazePresenter(CommonMaze maze) {
        this.maze = maze;
    }

    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String) null, var2);
        }
    }

    private void initializeInterface() {
        JFrame frame = new JFrame("Pacman Demo");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(350, 400);
        frame.setPreferredSize(new Dimension(350, 400));
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(true);
        frame.setFocusableWindowState(true);
        frame.addKeyListener(KeyboardController.getInstance());
        frame.addWindowListener(WindowController.getInstance());
        int rows = this.maze.numRows();
        int cols = this.maze.numCols();
        GridLayout layout = new GridLayout(rows, cols);
        JPanel content = new JPanel(layout);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                FieldView field = new FieldView(this.maze.getField(i, j));
                content.add(field);
            }
        }

        frame.getContentPane().add(content, "Center");
        frame.pack();
        frame.setVisible(true);
    }
}
