package ija.ija2022.project.game;

import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMaze;

import java.util.Arrays;

public class GhostObject extends BaseObject {
    public GhostObject(int row, int col, CommonMaze commonMaze) {
        super(row, col, commonMaze);
    }

    public void makeMove() {
        CommonField.Direction[] directions = CommonField.Direction.values();

        for (CommonField.Direction direction : directions) {
            if (!this.canMove(direction)) {
                directions = Arrays.stream(directions).filter(dir -> dir != direction).toArray(CommonField.Direction[]::new);
            }
        }

        if (directions.length == 0) return;

        int randomIndex = (int) (Math.random() * directions.length);
        CommonField.Direction randomDirection = directions[randomIndex];

        this.move(randomDirection);
    }
}
