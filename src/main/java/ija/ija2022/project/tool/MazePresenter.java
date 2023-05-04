package ija.ija2022.project.tool;

import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.view.FieldView;

import javax.swing.*;
import java.awt.*;

public class MazePresenter extends JPanel {
    private final CommonMaze maze;

    public MazePresenter(CommonMaze maze) {
        this.maze = maze;
        this.setLayout(new GridLayout(this.maze.numRows(), this.maze.numCols()));
        this.setSize(350, 400);

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
}
