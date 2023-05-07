package ija.ija2022.project.maze;

import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.objects.*;

import java.util.ArrayList;
import java.util.List;

public interface CommonMaze {
    CommonField getField(int row, int col);

    int numRows();

    int numCols();

    GhostObject[] ghosts();

    KeyObject[] keys();

    HeartObject[] hearts();

    TargetObject target();

    PacmanObject getPacman();

    void setField(int row, int col, CommonField iField);

    CommonField[][] getFields();

    ArrayList<CommonMazeObject>[][] getObjects();

    void putObject(CommonMazeObject object, int row, int col);

    void moveObject(CommonMazeObject object, int row, int col);

    void removeObject(CommonMazeObject object, int row, int col);

    void freezeGhosts();

    void unfreezeGhosts();

    void notifyUpdates();

    List<int[]> getPacmanPath();

    void setPacmanPath(List<int[]> pacmanPath);

    boolean isAllKeysCollected();
}
