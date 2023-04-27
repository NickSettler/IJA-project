package org.example.tool.common;

public interface IField extends Observable, CommonField {
    boolean put(IMazeObject object);

    boolean remove(IMazeObject object);

    void setMaze(IMaze commonMaze);
}
