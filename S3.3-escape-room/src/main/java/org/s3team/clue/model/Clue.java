package org.s3team.clue.model;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.room.model.Room;
import org.s3team.theme.model.Theme;

public class Clue {

    private final Id<Clue> id;
    private ClueDescription description;
    private Price price;
    private final ClueType type;
    private final Id<Theme> themeId;
    private final Id<Room> roomId;

    private Clue(Id<Clue> id, ClueType type, ClueDescription description, Price price, Id<Theme> themeId, Id<Room> roomId) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.themeId = themeId;
        this.roomId = roomId;
    }

    public static Clue createNew(ClueType type, ClueDescription description, Price price,
                                 Id<Theme> themeId, Id<Room> roomId) {
        return new Clue(null, type, description, price, themeId, roomId);
    }

    public static Clue rehydrate(Id<Clue> id, ClueType type, ClueDescription description, Price price, Id<Theme> themeId, Id<Room> roomId) {
        return new Clue(id, type, description, price, themeId, roomId);
    }

    public Id<Clue> getId() { return id; }
    public ClueType getType() { return type; }
    public ClueDescription getDescription() { return description; }
    public Price getPrice() { return price; }
    public Id<Theme> getThemeId() { return themeId; }
    public Id<Room> getRoomId() { return roomId; }

    @Override
    public String toString() {
        return "Clue = " +
                "id = " + id +
                ", description = " + description +
                ", price = " + price +
                ", theme = " + themeId +
                ", room = " + roomId;
    }
}
