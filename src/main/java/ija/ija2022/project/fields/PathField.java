package ija.ija2022.project.fields;

import java.util.List;

public class PathField extends BaseField implements CommonField {
    public PathField(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean isPacmanPath() {
        List<int[]> path = this.commonMaze.getPacmanPath();

        return path.stream().anyMatch(p -> p[0] == this.row && p[1] == this.col);
    }
}
