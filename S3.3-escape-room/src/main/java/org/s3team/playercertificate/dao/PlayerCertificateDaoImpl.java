package org.s3team.playercertificate.dao;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.Player.Model.Player;
import org.s3team.certificate.model.Certificate;
import org.s3team.common.valueobject.Id;
import org.s3team.playercertificate.model.PlayerCertificate;
import org.s3team.room.model.Room;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlayerCertificateDaoImpl implements PlayerCertificateDao {

    private final Data_Base_Connection db;

    public PlayerCertificateDaoImpl(Data_Base_Connection db) {
        this.db = db;
    }

    @Override
    public PlayerCertificate add(PlayerCertificate pc) throws SQLException {
        String sql = "INSERT INTO player_certificate (player_id, certificate_id, room_id) VALUES (?, ?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pc.getPlayerId().value());
            stmt.setInt(2, pc.getCertificateId().value());
            stmt.setInt(3, pc.getRoomId().value());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp(1);
                    LocalDateTime issuedDate = ts.toLocalDateTime();
                    return PlayerCertificate.rehydrate(
                            pc.getPlayerId(),
                            pc.getCertificateId(),
                            pc.getRoomId(),
                            issuedDate
                    );
                }
            }
        }

        throw new SQLException("Creating PlayerCertificate failed, no issued_date returned.");
    }

    @Override
    public List<PlayerCertificate> findByPlayer(Id<Player> playerId) throws SQLException {
        String sql = "SELECT * FROM player_certificate WHERE player_id = ?";
        List<PlayerCertificate> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerId.value());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(PlayerCertificate.rehydrate(
                            new Id<>(rs.getInt("player_id")),
                            new Id<>(rs.getInt("certificate_id")),
                            new Id<>(rs.getInt("room_id")),
                            rs.getTimestamp("issued_date").toLocalDateTime()
                    ));
                }
            }
        }

        return list;
    }

    @Override
    public List<PlayerCertificate> findByRoom(Id<Room> roomId) throws SQLException {
        String sql = "SELECT * FROM player_certificate WHERE room_id = ?";
        List<PlayerCertificate> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId.value());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(PlayerCertificate.rehydrate(
                            new Id<>(rs.getInt("player_id")),
                            new Id<>(rs.getInt("certificate_id")),
                            new Id<>(rs.getInt("room_id")),
                            rs.getTimestamp("issued_date").toLocalDateTime()
                    ));
                }
            }
        }

        return list;
    }

    @Override
    public List<PlayerCertificate> findAll() throws SQLException {
        String sql = "SELECT * FROM player_certificate";
        List<PlayerCertificate> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(PlayerCertificate.rehydrate(
                        new Id<>(rs.getInt("player_id")),
                        new Id<>(rs.getInt("certificate_id")),
                        new Id<>(rs.getInt("room_id")),
                        rs.getTimestamp("issued_date").toLocalDateTime()
                ));
            }
        }

        return list;
    }

    @Override
    public boolean exists(Id<Player> playerId, Id<Certificate> certificateId, Id<Room> roomId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM player_certificate WHERE player_id = ? AND certificate_id = ? AND room_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerId.value());
            stmt.setInt(2, certificateId.value());
            stmt.setInt(3, roomId.value());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }
}
