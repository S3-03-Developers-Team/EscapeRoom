package org.s3team.playercertificate.dao;

import org.s3team.Player.Model.Player;
import org.s3team.certificate.model.Certificate;
import org.s3team.common.valueobject.Id;
import org.s3team.playercertificate.dto.PlayerCertificateInfo;
import org.s3team.playercertificate.model.PlayerCertificate;
import org.s3team.room.model.Room;

import java.sql.SQLException;
import java.util.List;

public interface PlayerCertificateDao {

    PlayerCertificate add(PlayerCertificate pc) throws SQLException;
    List<PlayerCertificate> findByPlayer(Id<Player> playerId) throws SQLException;
    List<PlayerCertificate> findByRoom(Id<Room> roomId) throws SQLException;
    List<PlayerCertificate> findAll() throws SQLException;
    boolean exists(Id<Player> playerId, Id<Certificate> certificateId, Id<Room> roomId) throws SQLException;

    List<PlayerCertificateInfo> findAllWithInfo() throws SQLException;
    List<PlayerCertificateInfo> findCertificatesByPlayerWithInfo(Id<Player> playerId) throws SQLException;
    List<PlayerCertificateInfo> findCertificatesByRoomWithInfo(Id<Room> roomId) throws SQLException;
}
