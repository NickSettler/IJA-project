package ija.ija2022.project.tool.view;

import ija.ija2022.project.game.PacmanObject;
import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.CommonMazeObject;
import ija.ija2022.project.tool.common.Observable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FieldView extends JPanel implements Observable.Observer {
    private final CommonField field;
    private final List<ComponentView> objects;
    private int changedModel = 0;

    public FieldView(CommonField field) {
        this.field = field;
        this.objects = new ArrayList<>();
        this.privUpdate();
        field.addObserver(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < this.objects.size(); i++)
            this.objects.get(i).paintComponent(g);
    }

    private void privUpdate() {
        if (this.field.canMove()) {
            this.setBackground(Color.white);
            if (!this.field.isEmpty()) {
                ArrayList<CommonMazeObject> objects = this.field.get();

                PacmanObject pacman = objects.stream()
                        .filter(o -> o instanceof PacmanObject)
                        .map(o -> (PacmanObject) o)
                        .findFirst()
                        .orElse(null);

                if (objects.size() > 1) {
                    System.out.println("Here");
                }

                ComponentView v = pacman != null ? new PacmanView(this, pacman) : new GhostView(this, objects.get(0));
                this.objects.add(v);
            } else {
                this.objects.clear();
            }
        } else {
            this.setBackground(Color.lightGray);
        }

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public final void update(Observable observable) {
        ++this.changedModel;
        this.privUpdate();
    }

    public int numberUpdates() {
        return this.changedModel;
    }

    public void clearChanged() {
        this.changedModel = 0;
    }

    public CommonField getField() {
        return this.field;
    }
}
