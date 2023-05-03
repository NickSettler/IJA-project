package ija.ija2022.project.tool.common;

import ija.ija2022.project.game.PacmanObject;

import java.util.List;

public interface CommonMaze extends Observable.Observer {
    CommonField getField(int row, int col);

    int numRows();

    int numCols();

    List<CommonMazeObject> ghosts();

    void setField(int row, int col, CommonField iField);

    CommonField[][] getFields();

    void putObject(CommonMazeObject object, int row, int col);

    void moveObject(CommonMazeObject object, int row, int col);

    PacmanObject getPacman();

    void removeObject(int row, int col);
}
