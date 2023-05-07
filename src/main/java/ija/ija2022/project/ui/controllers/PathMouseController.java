package ija.ija2022.project.ui.controllers;

import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.events.events.PathFieldMouseClickEvent;
import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.fields.FieldView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PathMouseController implements MouseListener {
    private static PathMouseController instance;

    public static PathMouseController getInstance() {
        if (instance == null) {
            instance = new PathMouseController();
        }

        return instance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CommonField field = ((FieldView) e.getSource()).getField();
        EventManager.getInstance().fireEvent(new PathFieldMouseClickEvent(field));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
