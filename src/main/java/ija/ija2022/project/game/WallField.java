package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;

public class WallField extends BaseField implements CommonField {
    public WallField(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
