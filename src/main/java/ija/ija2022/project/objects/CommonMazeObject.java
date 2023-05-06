package ija.ija2022.project.objects;

import ija.ija2022.project.common.Observable;
import ija.ija2022.project.fields.CommonField;

public interface CommonMazeObject extends Observable {
    boolean canMove(CommonField.Direction direction);

    boolean canMove(int row, int col);

    void move();

    void move(CommonField.Direction direction);

    void move(int row, int col);

    default boolean isPacman() {
        return false;
    }

    default int getLives() {
        return 0;
    }

    void setDirection(CommonField.Direction direction);

    CommonField.Direction getDirection();

    int getRow();

    int getCol();
}
