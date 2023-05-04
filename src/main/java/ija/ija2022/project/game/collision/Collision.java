package ija.ija2022.project.game.collision;

import ija.ija2022.project.tool.common.CommonMazeObject;
import javafx.util.Pair;

import java.util.function.Consumer;

public class Collision {
    private final CommonMazeObject a;
    private final CommonMazeObject b;

    private final Consumer<Pair<CommonMazeObject, CommonMazeObject>> handler;

    public Collision(
            CommonMazeObject a,
            CommonMazeObject b,
            Consumer<Pair<CommonMazeObject, CommonMazeObject>> handler) {
        this.a = a;
        this.b = b;
        this.handler = handler;
    }

    public void handle() {
        this.handler.accept(new Pair<>(this.a, this.b));
    }
}
