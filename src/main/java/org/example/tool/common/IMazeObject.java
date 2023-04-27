package org.example.tool.common;

public interface IMazeObject extends CommonMazeObject, Observable {
    int getRow();

    int getCol();
}
