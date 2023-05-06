package ija.ija2022.project.maze;

import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.objects.CommonMazeObject;
import ija.ija2022.project.objects.GhostObject;
import ija.ija2022.project.objects.PacmanObject;

import java.util.ArrayList;

public interface CommonMaze {
    CommonField getField(int row, int col);

    int numRows();

    int numCols();

    GhostObject[] ghosts();

    PacmanObject getPacman();

    void setField(int row, int col, CommonField iField);

    CommonField[][] getFields();

    ArrayList<CommonMazeObject>[][] getObjects();

    void putObject(CommonMazeObject object, int row, int col);

    void moveObject(CommonMazeObject object, int row, int col);

    void removeObject(CommonMazeObject object, int row, int col);

    void notifyUpdates();
}
