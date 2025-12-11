package org.s3team.decoration.model;

import java.math.BigDecimal;

public class Decoration {

    private int idDecorationObject;
    private String name;
    private Material material;
    private int stock;
    private BigDecimal price;
    private int roomId;

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

