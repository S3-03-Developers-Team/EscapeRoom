package org.s3team.exceptions;

import org.s3team.player.model.Player;
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
