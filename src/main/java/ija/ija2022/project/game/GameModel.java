package ija.ija2022.project.game;

import ija.ija2022.project.maze.Maze;

public class GameModel {
    private int score;
    private boolean isGameOver;
    private final Maze maze;

    public GameModel(Maze maze) {
        score = 0;
        isGameOver = false;
        this.maze = maze;
    }

    public void update() {

    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getScore() {
        return score;
    }

    public Maze getMaze() {
        return maze;
    }
}