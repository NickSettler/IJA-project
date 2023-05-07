/**
 * AStar path finding node implementation.
 * This class is used to represent a node in the AStar algorithm.
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.astar;

import ija.ija2022.project.fields.BaseField;
import ija.ija2022.project.fields.CommonField;

public class Node {
    private final BaseField field;
    private final Node parent;
    private final int g;
    private final int h;
    private final int f;
    private final CommonField.Direction direction;

    public Node(BaseField field, Node parent, int g, int h, CommonField.Direction direction) {
        this.field = field;
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
        return field == node.field;
    }

    @Override
    public int hashCode() {
        return field.hashCode();
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public BaseField getField() {
        return field;
    }

    public Node getParent() {
        return parent;
    }

    public CommonField.Direction getDirection() {
        return direction;
    }
}
