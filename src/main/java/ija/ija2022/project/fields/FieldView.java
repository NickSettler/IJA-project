package ija.ija2022.project.fields;

import ija.ija2022.project.common.ComponentView;
import ija.ija2022.project.common.Observable;
import ija.ija2022.project.objects.*;

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
            if (this.field.isPacmanPath())
                this.setBackground(Color.green.brighter());
            else
                this.setBackground(Color.gray);

            this.objects.clear();

            ArrayList<CommonMazeObject> objects = this.field.get();

            objects.stream().forEach(o -> {
                ComponentView v = null;
                if (o instanceof PacmanObject)
                    v = new PacmanView(this, (PacmanObject) o);
                else if (o instanceof KeyObject)
                    v = new KeyView(this, o);
                else if (o instanceof GhostObject)
                    v = new GhostView(this, o);

                if (v != null)
                    this.objects.add(v);
            });
        } else {
            this.objects.add(new WallView(this));
        }

        updateUI();
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
