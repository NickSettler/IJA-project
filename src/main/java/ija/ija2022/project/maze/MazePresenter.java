package ija.ija2022.project.maze;

import ija.ija2022.project.fields.FieldView;

import javax.swing.*;
import java.awt.*;

public class MazePresenter extends JPanel {
    private final CommonMaze maze;

    public MazePresenter(CommonMaze maze) {
        this.maze = maze;
        this.setLayout(new GridLayout(this.maze.numRows(), this.maze.numCols()));
        this.setSize(350, 400);
        this.setBackground(Color.gray);

        this.initializeInterface();
    }

    private void initializeInterface() {
        for (int i = 0; i < this.maze.numRows(); ++i) {
            for (int j = 0; j < this.maze.numCols(); ++j) {
                FieldView field = new FieldView(this.maze.getField(i, j));
                this.add(field);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Rectangle bounds = this.getParent().getBounds();

        if (bounds == null)
            return super.getPreferredSize();

        int squareSize = Math.min(bounds.width / this.maze.numCols(), bounds.height / this.maze.numRows());
        int width = squareSize * this.maze.numCols();
        int height = squareSize * this.maze.numRows();

        return new Dimension(width, height);
    }
}
