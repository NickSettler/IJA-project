package ija.ija2022.project.game;

import java.util.List;

import ija.ija2022.project.tool.common.CommonMazeObject;

public class GameState {
    private boolean isGameOver;
    private List<CommonMazeObject> mazeObjectList;

    public GameState(boolean isGameOver, List<CommonMazeObject> mazeObjectList) {
        this.isGameOver = isGameOver;
        this.mazeObjectList = mazeObjectList;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public List<CommonMazeObject> getMazeObjectList() {
        return mazeObjectList;
    }
}