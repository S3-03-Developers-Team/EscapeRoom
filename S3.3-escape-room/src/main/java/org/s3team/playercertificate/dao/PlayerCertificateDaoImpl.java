package org.s3team.playercertificate.dao;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.Player.Model.Player;
import org.s3team.certificate.model.Certificate;
import org.s3team.common.valueobject.Id;
import org.s3team.playercertificate.dto.PlayerCertificateInfo;
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

    private static final String SQL_SELECT_ALL_WITH_INFO = """
         SELECT pc.player_id, p.name AS player_name,
                pc.certificate_id, c.type AS certificate_type, c.reward AS certificate_reward,
                pc.room_id, r.name AS room_name,
                pc.issued_date
         FROM player_certificate pc
            JOIN player p ON pc.player_id = p.id_player
            JOIN certificate c ON pc.certificate_id = c.id_certificate
            JOIN room r ON pc.room_id = r.id_room
        """;

    private static final String SQL_SELECT_BY_PLAYER_WITH_INFO = SQL_SELECT_ALL_WITH_INFO + " WHERE pc.player_id = ?";

    private static final String SQL_SELECT_BY_ROOM_WITH_INFO = SQL_SELECT_ALL_WITH_INFO + " WHERE pc.room_id = ?";

    @Override
    public PlayerCertificate add(PlayerCertificate pc) throws SQLException {
        String insertSql = "INSERT INTO player_certificate (player_id, certificate_id, room_id) VALUES (?, ?, ?)";
        String selectSql = "SELECT issued_date FROM player_certificate WHERE player_id = ? AND certificate_id = ? AND room_id = ?";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, pc.getPlayerId().value());
                insertStmt.setInt(2, pc.getCertificateId().value());
                insertStmt.setInt(3, pc.getRoomId().value());
                insertStmt.executeUpdate();
            }

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, pc.getPlayerId().value());
                selectStmt.setInt(2, pc.getCertificateId().value());
                selectStmt.setInt(3, pc.getRoomId().value());

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        Timestamp ts = rs.getTimestamp("issued_date");
                        LocalDateTime issuedDate = ts.toLocalDateTime();

                        conn.commit(); // Confirmamos la transacción

                        return PlayerCertificate.rehydrate(
                                pc.getPlayerId(),
                                pc.getCertificateId(),
                                pc.getRoomId(),
                                issuedDate
                        );
                    } else {
                        conn.rollback();
                        throw new SQLException("Creating PlayerCertificate failed, no issued_date found.");
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
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

    @Override
    public List<PlayerCertificateInfo> findAllWithInfo() throws SQLException {

        List<PlayerCertificateInfo> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL_WITH_INFO);
             ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    list.add(new PlayerCertificateInfo(
                            new Id<>(rs.getInt("player_id")),
                            rs.getString("player_name"),
                            new Id<>(rs.getInt("certificate_id")),
                            rs.getString("certificate_type"),
                            rs.getString("certificate_reward"),
                            new Id<>(rs.getInt("room_id")),
                            rs.getString("room_name"),
                            rs.getTimestamp("issued_date").toLocalDateTime()
                    ));
                }
            }
        return list;
    }



    @Override
    public List<PlayerCertificateInfo> findCertificatesByPlayerWithInfo(Id<Player> playerId) throws SQLException {
        List<PlayerCertificateInfo> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_PLAYER_WITH_INFO)) {

            stmt.setInt(1, playerId.value());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new PlayerCertificateInfo(
                            new Id<>(rs.getInt("player_id")),
                            rs.getString("player_name"),
                            new Id<>(rs.getInt("certificate_id")),
                            rs.getString("certificate_type"),
                            rs.getString("certificate_reward"),
                            new Id<>(rs.getInt("room_id")),
                            rs.getString("room_name"),
                            rs.getTimestamp("issued_date").toLocalDateTime()
                    ));
                }
            }
        }

        return list;
    }

    @Override
    public List<PlayerCertificateInfo> findCertificatesByRoomWithInfo(Id<Room> roomId) throws SQLException {

        List<PlayerCertificateInfo> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ROOM_WITH_INFO)) {

            stmt.setInt(1, roomId.value());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new PlayerCertificateInfo(
                            new Id<>(rs.getInt("player_id")),
                            rs.getString("player_name"),
                            new Id<>(rs.getInt("certificate_id")),
                            rs.getString("certificate_type"),
                            rs.getString("certificate_reward"),
                            new Id<>(rs.getInt("room_id")),
                            rs.getString("room_name"),
                            rs.getTimestamp("issued_date").toLocalDateTime()
                    ));
                }
            }
        }

        return list;
    }
}
