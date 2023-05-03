package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;

public class PathField extends BaseField implements CommonField {
    public PathField(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean canMove() {
        return true;
    }
}
