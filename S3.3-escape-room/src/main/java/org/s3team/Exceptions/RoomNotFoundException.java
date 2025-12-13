package org.s3team.Exceptions;

import org.s3team.common.valueobject.Id;
import org.s3team.room.model.Room;

public class RoomNotFoundException extends RuntimeException {
    private final Id<Room> id;

    public RoomNotFoundException(Id<Room> id) {
        super("Room not found: " + id);
        this.id = id;
    }

    public Id<Room> getId() {
        return id;
    }
}
