package ija.ija2022.project.astar;

import ija.ija2022.project.tool.common.CommonField;

import java.awt.*;
import java.util.Objects;

public class Node {
    private final Point point;
    private final Node parent;
    private final int g;
    private final int h;
    private final int f;
    private final CommonField.Direction direction;

    public Node(Point point, Node parent, int g, int h, CommonField.Direction direction) {
        this.point = point;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return point == node.point;
    }

    @Override
    public int hashCode() {
        return Objects.hash(f);
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }
    public Point getPoint() {
        return point;
    }

    public Node getParent() {
        return parent;
    }

    public CommonField.Direction getDirection() {
        return direction;
    }
}
