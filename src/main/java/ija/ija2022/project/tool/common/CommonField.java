package ija.ija2022.project.tool.common;

import java.util.ArrayList;

public interface CommonField extends Observable {
    CommonField nextField(Direction var1);

    boolean isEmpty();

    ArrayList<CommonMazeObject> get();

    boolean canMove();

    boolean contains(CommonMazeObject var1);


    void put(CommonMazeObject object);

    void remove(CommonMazeObject object);

    void setMaze(CommonMaze commonMaze);

    public static enum Direction {
        L(0, -1),
        U(-1, 0),
        R(0, 1),
        D(1, 0);

        private final int r;
        private final int c;

        private Direction(int dr, int dc) {
            this.r = dr;
            this.c = dc;
        }

        public int deltaRow() {
            return this.r;
        }

        public int deltaCol() {
            return this.c;
        }
    }
}
