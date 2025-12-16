package org.s3team.room.model;
import org.s3team.common.valueobject.*;
import org.s3team.theme.model.Theme;
import java.util.Objects;

public class Room {
    private final Id<Room> roomId;
    private final Name name;
    private final Difficulty difficulty;
    private final Price price;
    private final Id<Theme> themeId;


    private Room(Id<Room> roomId, Name name, Difficulty difficulty, Price price, Id<Theme> themeId) {
        this.roomId = roomId;
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
        this.themeId = themeId;
    }


    public static Room createNew(Name name, Difficulty difficulty, Price price, Id<Theme> themeId) {
        return new Room(null, name, difficulty, price, themeId);
    }

    public static Room rehydrate(Id<Room> roomId, Name name, Difficulty difficulty, Price price, Id<Theme> themeId) {

        if (roomId == null) {
            throw new IllegalArgumentException("Rehydrating entity must have a valid ID.");
        }
        return new Room(roomId, name, difficulty, price, themeId);
    }


    public Id<Room>  getRoomId() {
        return roomId;
    }


    public Name getName() {
        return name;
    }


    public Difficulty getDifficulty() {
        return difficulty;
    }


    public Price getPrice() {
        return price;
    }

    public Id<Theme> getThemeId() {
        return themeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId) &&
                Objects.equals(name, room.name) &&
                difficulty == room.difficulty &&
                Objects.equals(price, room.price) &&
                Objects.equals(themeId, room.themeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, name, difficulty, price, themeId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", price=" + price +
                ", themeId=" + themeId +
                '}';
    }


}
