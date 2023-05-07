package ija.ija2022.project.collisions;

import ija.ija2022.project.objects.CommonMazeObject;
import ija.ija2022.project.utils.Pair;

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
