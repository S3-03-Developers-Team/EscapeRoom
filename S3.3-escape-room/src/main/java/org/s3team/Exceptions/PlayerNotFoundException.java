package org.s3team.Exceptions;

import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;

public class PlayerNotFoundException extends RuntimeException {
    private final Id<Player> id;

    public PlayerNotFoundException(Id<Player> id) {
        super("Player not found: " + id);
        this.id = id;
    }

    public Id<Player> getId() {
        return id;
    }
}
