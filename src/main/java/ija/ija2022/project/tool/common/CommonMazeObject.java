package ija.ija2022.project.tool.common;

public interface CommonMazeObject extends Observable {
    boolean canMove(CommonField.Direction var1);

    boolean move(CommonField.Direction var1);

    default boolean isPacman() {
        return false;
    }

    CommonField getField();

    int getLives();

    int getRow();

    int getCol();
}
