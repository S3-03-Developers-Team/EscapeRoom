package org.s3team.room.model;
import org.s3team.common.valueobject.*;
import org.s3team.theme.model.Theme;

import java.math.BigDecimal;
import java.util.Objects;

public class Room {
    private Id<Room> roomId;  // Id de la sala, generado automáticamente por la base de datos
    private Name name;     // Nombre de la sala
    private room.Difficulty difficulty; // Nivel de dificultad de la sala
    private Price price;// Precio de la sala
    private Id<Theme> themeId;

    protected Room() {
    }

    private Room(Id<Room> roomId, Name name, room.Difficulty difficulty, Price price, Id<Theme> themeId) {
        this.roomId = roomId;
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
        this.themeId = themeId;
    }

    public static Room createNew(Name name, room.Difficulty difficulty, Price price, Id<Theme> themeId) {
        return new Room(null, name, difficulty, price, themeId);
    }

    public static Room rehydrate(Id<Room> roomId, Name name, room.Difficulty difficulty, Price price, Id<Theme> themeId) {

        if (roomId == null) {
            throw new IllegalArgumentException("Rehydrating entity must have a valid ID.");
        }
        return new Room(roomId, name, difficulty, price, themeId);
    }



    public Id<Room>  getRoomId() {
        return roomId;
    }

    public void setRoomId(Id<Room> roomId) {
        this.roomId = roomId;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public room.Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(room.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Id<Theme> getThemeId() {
        return themeId;
    }

    public void setThemeId(Id<Theme> themeId) {
        this.themeId = themeId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId
                , room.roomId
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId
        );
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
