package org.s3team.playercertificate.dto;

import org.s3team.Player.Model.Player;
import org.s3team.certificate.model.Certificate;
import org.s3team.common.valueobject.Id;
import org.s3team.room.model.Room;

import java.time.LocalDateTime;

public record PlayerCertificateInfo(
    Id<Player> playerId,
    String playerName,
    Id<Certificate> certificateId,
    String certificateType,
    String reward,
    Id<Room> roomId,
    String roomName,
    LocalDateTime issuedDate
) {
    @Override
    public String toString() {
        return String.format(
                "PlayerCertificateInfo { playerId=%s, playerName='%s', certificateId=%s, certificateType='%s', reward='%s', roomId=%s, roomName='%s', issuedDate=%s }",
                playerId,
                playerName,
                certificateId,
                certificateType,
                reward,
                roomId,
                roomName,
                issuedDate
        );
    }
}
