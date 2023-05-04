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

    private final JFrame frame;

    public MazePresenter(CommonMaze maze) {
        this.maze = maze;
        this.frame = new JFrame();
    }

    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String) null, var2);
        }
    }

    private void initializeInterface() {
        this.frame.setTitle("Pacman Demo");
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.setSize(350, 400);
        this.frame.setPreferredSize(new Dimension(350, 400));
        this.frame.setResizable(false);
        this.frame.setFocusable(true);
        this.frame.setFocusTraversalKeysEnabled(true);
        this.frame.setFocusableWindowState(true);
        this.frame.addKeyListener(KeyboardController.getInstance());
        this.frame.addWindowListener(WindowController.getInstance());
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

        this.frame.getContentPane().add(content, "Center");
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
