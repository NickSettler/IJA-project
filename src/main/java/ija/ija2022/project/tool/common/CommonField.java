package ija.ija2022.project.tool.common;

import java.util.ArrayList;

public interface CommonField extends Observable {
    CommonField nextField(Direction var1);

    boolean isEmpty();

    ArrayList<CommonMazeObject> get();

    boolean canMove();

    void setMaze(CommonMaze commonMaze);

    int getRow();

    int getCol();

    public static enum Direction {
        L(0, -1),
        U(-1, 0),
        R(0, 1),
        D(1, 0),
        N(0, 0);

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

        public Direction opposite() {
            return switch (this) {
                case L -> R;
                case U -> D;
                case R -> L;
                case D -> U;
                default -> N;
            };
        }

        public static Direction from(int dr, int dc) {
            for (Direction direction : Direction.values()) {
                if (direction.deltaRow() == dr && direction.deltaCol() == dc) {
                    return direction;
                }
            }

            return null;
        }
    }
}
