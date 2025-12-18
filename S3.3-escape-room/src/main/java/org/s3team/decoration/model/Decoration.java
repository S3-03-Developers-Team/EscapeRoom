package org.s3team.decoration.model;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.room.model.Room;

import java.math.BigDecimal;
import java.util.Objects;

public class Decoration {

    private Id<Decoration> idDecorationObject;

    private Id<Decoration> decorationId;
    private Name name;
    private Material material;
    private Integer stock;
    private Price price;
    private Id<Room> roomId;

    private Decoration(Id<Decoration> decorationId, Name name, Material material, Integer stock, Price price, Id<Room> roomId) {
        this.decorationId = decorationId;
        this.name = name;
        this.material = material;
        this.stock = stock;
        this.price = price;
        this.roomId = roomId;
    }

    public static Decoration createNew(Name name, Material material, Integer stock, Price price, Id<Room> roomId) {
        return new Decoration(null, name, material,stock, price, roomId);
    }

    public static Decoration rehydrate(Id<Decoration> decorationId, Name name, Material material, Integer stock, Price price, Id<Room> roomId) {

        if (decorationId == null) {
            throw new IllegalArgumentException("Rehydrating entity must have a valid ID.");
        }
        return new Decoration(decorationId, name, material,stock, price, roomId);
    }

    public Id<Decoration> getDecorationId() {
        return decorationId;
    }

    public Name getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getStock() {
        return stock;
    }

    public Price getPrice() {
        return price;
    }

    public Id<Room> getRoomId() {
        return roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Decoration that = (Decoration) o;
        return Objects.equals(decorationId, that.decorationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(decorationId);
    }

    @Override
    public String toString() {
        return "Decoration{" +
                "decorationId=" + decorationId +
                ", name=" + name +
                ", material=" + material +
                ", stock=" + stock +
                ", price=" + price +
                ", roomId=" + roomId +
                '}';
    }
}

