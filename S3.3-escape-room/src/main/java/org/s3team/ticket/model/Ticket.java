package org.s3team.ticket.model;

import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.room.model.Room;

import java.time.LocalDateTime;

public class Ticket {

    private final Id<Ticket> id;
    private final LocalDateTime purchaseDate;
    private final Price total;
    private final Id<Player> playerId;
    private final Id<Room> roomId;

    private Ticket(Id<Ticket> id, LocalDateTime purchaseDate, Price total, Id<Player> playerId, Id<Room> roomId ) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.total = total;
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public static Ticket createNew(Price total, Id<Player> playerId, Id<Room> roomId) {
        return new Ticket(null, null, total, playerId, roomId);
    }

    public static Ticket rehydrate(Id<Ticket> id, LocalDateTime purchaseDate, Price total, Id<Player> playerId, Id<Room> roomId) {
        return new Ticket(id, purchaseDate, total, playerId, roomId);
    }

    public Id<Ticket> getId() {
        return id;
    }
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
    public Price getTotal() {
        return total;
    }
    public Id<Player> getPlayerId() {
        return playerId;
    }
    public Id<Room> getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Ticket = " +
                "id = " + id +
                ", purchaseDate = " + purchaseDate +
                ", total = " + total +
                ", playerId = " + playerId +
                ", roomId = " + roomId;
    }
}
