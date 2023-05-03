package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMazeObject;

public class WallField extends BaseField implements CommonField {
    public WallField(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void put(CommonMazeObject object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(CommonMazeObject object) {
        throw new UnsupportedOperationException();
    }
}
