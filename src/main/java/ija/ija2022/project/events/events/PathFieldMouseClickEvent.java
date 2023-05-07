package ija.ija2022.project.events.events;

import ija.ija2022.project.events.Event;
import ija.ija2022.project.fields.CommonField;

public class PathFieldMouseClickEvent extends Event {
    private CommonField field;

    public PathFieldMouseClickEvent(CommonField field) {
        this.field = field;
    }

    public CommonField getField() {
        return this.field;
    }
}
