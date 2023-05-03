package ija.ija2022.project.tool.common;

import ija.ija2022.project.game.GhostObject;
import ija.ija2022.project.game.PacmanObject;

import java.util.ArrayList;

public interface CommonMaze {
    CommonField getField(int row, int col);

    int numRows();

    int numCols();

    GhostObject[] ghosts();

    void setField(int row, int col, CommonField iField);

    CommonField[][] getFields();

    ArrayList<CommonMazeObject>[][] getObjects();

    void putObject(CommonMazeObject object, int row, int col);

    void moveObject(CommonMazeObject object, int row, int col);

    PacmanObject getPacman();
}
