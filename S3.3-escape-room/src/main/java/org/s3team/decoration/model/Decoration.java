package org.s3team.decoration.model;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.room.model.Room;

import java.math.BigDecimal;

public class Decoration {

    private Id<Decoration> idDecorationObject;
    private Name name;
    private Material material;
    private Integer stock;
    private Price price;
    private Id<Room> roomId;

    public Decoration() {}

    public Decoration(String name, Material material, int stock, BigDecimal price, int roomId) {
        this.name = name;
        this.material = material;
        this.stock = stock;
        this.price = price;
        this.roomId = roomId;
    }


    public Decoration(int idDecorationObject, String name, Material material, int stock, BigDecimal price, int roomId) {
        this.idDecorationObject = idDecorationObject;
        this.name = name;
        this.material = material;
        this.stock = stock;
        this.price = price;
        this.roomId = roomId;
    }

    public int getIdDecorationObject() { return idDecorationObject; }
    public String getName() {
        return name;
    }
    public Material getMaterial() {
        return material;
    }
    public int getStock() {
        return stock;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public int getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Decoration: " + name + " (" + material + ") - " + price + "€ [Stock: " + stock + "]";
    }
}

