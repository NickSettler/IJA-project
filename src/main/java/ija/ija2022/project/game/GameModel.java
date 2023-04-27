package ija.ija2022.project.game;

public class GameModel {
    private int score;
    private boolean isGameOver;

    public GameModel() {
        score = 0;
        isGameOver = false;
    }

    public void update() {

    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getScore() {
        return score;
    }
}