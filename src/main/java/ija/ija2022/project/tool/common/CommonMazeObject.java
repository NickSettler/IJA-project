package ija.ija2022.project.tool.common;

public interface CommonMazeObject extends Observable {
    boolean canMove(CommonField.Direction direction);

    void move();

    void move(CommonField.Direction direction);

    default boolean isPacman() {
        return false;
    }

    default int getLives() {
        return 0;
    }

    void setDirection(CommonField.Direction direction);

    int getRow();

    int getCol();
}
