package org.s3team.theme.model;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;

public class Theme {

    private final Id<Theme> id;
    private final Name name;

    private Theme(Id<Theme> id, Name name) {
        this.id = id;
        this.name = name;
    }

    public static Theme createNew(Name name) {
        return new Theme(null, name);
    }

    public static Theme rehydrate(Id<Theme> id, Name name) {
        return new Theme(id, name);
    }

    public Id<Theme> getId() { return id; }
    public Name getName() { return name; }

    @Override
    public String toString() {
        return "id=%s, name=%s".formatted(getId(), getName());
    }
}