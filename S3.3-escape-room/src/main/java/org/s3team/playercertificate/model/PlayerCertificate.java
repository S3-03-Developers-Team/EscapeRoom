package org.s3team.playercertificate.model;

import org.s3team.Player.Model.Player;
import org.s3team.certificate.model.Certificate;
import org.s3team.common.valueobject.Id;
import org.s3team.room.model.Room;

import java.time.LocalDateTime;

public class PlayerCertificate {

    private final Id<Player> playerId;
    private final Id<Certificate> certificateId;
    private final Id<Room> roomId;
    private final LocalDateTime issuedDate;

    private PlayerCertificate(Id<Player> playerId, Id<Certificate> certificateId, Id<Room> roomId, LocalDateTime issuedDate){
        this.playerId = playerId;
        this.certificateId = certificateId;
        this.roomId = roomId;
        this.issuedDate = issuedDate;
    }

    public static PlayerCertificate createNew(Id<Player> playerId, Id<Certificate> certificateId, Id<Room> roomId) {
        return new PlayerCertificate(playerId, certificateId, roomId, null);
    }

    public static PlayerCertificate rehydrate(Id<Player> playerId, Id<Certificate> certificateId, Id<Room> roomId, LocalDateTime issuedDate) {
        return new PlayerCertificate(playerId, certificateId, roomId, issuedDate);
    }

    public Id<Player> getPlayerId() { return playerId; }
    public Id<Certificate> getCertificateId() { return certificateId; }
    public Id<Room> getRoomId() { return roomId; }
    public LocalDateTime getIssuedDate() { return issuedDate; }

    @Override
    public String toString() {
        return "PlayerCertificate = " +
                "playerId = " + playerId +
                ", certificateId = " + certificateId +
                ", roomId = " + roomId +
                ", issuedDate = " + issuedDate;
    }
}
