package org.s3team.Exceptions;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.theme.model.Theme;

public class ThemeNotFoundException extends RuntimeException {
    private final String identifier;

    public ThemeNotFoundException(Id<Theme> id) {
        super("Theme not found: " + id);
        this.identifier = id.toString();
    }

    public ThemeNotFoundException(Name name) {
        super("Theme not found: " + name);
        this.identifier = name.toString();
    }

    public String getIdentifier() {
        return identifier;
    }
}
