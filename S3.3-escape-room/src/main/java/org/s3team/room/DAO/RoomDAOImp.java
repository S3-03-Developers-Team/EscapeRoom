package org.s3team.room.DAO;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.common.valueobject.*;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;
import org.s3team.theme.model.Theme;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDAOImp implements RoomDAO {

    private final Data_Base_Connection db;

    public RoomDAOImp(Data_Base_Connection  db) {
        this.db = db;
    }

    @Override
    public Room save(Room room) {
        final String sql = "insert into room(name,difficulty,price,theme_id)values(?,?,?,?)";
        Room savedRoom = null;

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, room.getName().value());
            ps.setString(2, room.getDifficulty().toString());
            ps.setBigDecimal(3, room.getPrice().value());
            ps.setInt(4, room.getThemeId().value());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Database error: Room creation failed, no rows affected.");
            }
            //El siguiente código asigna directamente el ID generado por MySQL al objeto para facilitar las consultas por ID.
            int newIdValue = 0;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newIdValue = generatedKeys.getInt(1);
                    Id<Room> newId = new Id<>(newIdValue);
                } else {
                    throw new RuntimeException("Room saved but failed to retrieve generated ID.");
                }
            }

            savedRoom = Room.rehydrate(
                    new Id<Room>(newIdValue),
                    new Name(room.getName().value()),
                    room.getDifficulty(),
                    new Price(room.getPrice().value()),
                    new Id<Theme>(room.getThemeId().value())
            );

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error saving Room: " + room.getName(), e);
        }return savedRoom;
    }

    @Override
    public Optional<Room> findById(Id<Room> id) {
        final String SQL = "SELECT id_room, name, difficulty, price, theme_id FROM room WHERE id_room = ?";
        int roomIdValue = id.value();
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, roomIdValue);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapRowToRoom(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error occurred while finding Room by ID: " + id);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        final String SQL = "SELECT id_room, name, difficulty, price, theme_id FROM room";

        List<Room> rooms = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rooms.add(mapRowToRoom(rs));
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred while finding all Rooms: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
        return rooms;
    }

    @Override
    public boolean update(Room room) {
        final String sql = "UPDATE room SET name = ?, difficulty = ?, price = ?, theme_id = ? WHERE id_room = ?";
        if (room.getRoomId() == null) {
            throw new IllegalArgumentException("Cannot update Room: ID is missing.");
        }
        int roomIdValue = room.getRoomId().value();
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, room.getName().value());
            ps.setString(2, room.getDifficulty().toString());
            ps.setBigDecimal(3, room.getPrice().value());
            ps.setInt(4, room.getThemeId().value());
            ps.setInt(5, roomIdValue);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error updating Room: " + room.getName(), e);
        }
    }

    @Override
    public boolean delete(Id<Room> id) {
        int roomId = id.value();
        final String sql = "DELETE FROM room WHERE id_room = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error deleting Room with ID: " + roomId, e);
        }
    }

    @Override
    public int count() {
        final String sql = "SELECT COUNT(*) FROM room";
        int count = 0;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error counting Rooms.", e);
        }
        return count;
    }

    @Override
    public Price calculateTotalPrice() {
        final String sql = "SELECT SUM(price) FROM room";
        Price totalPrice = new Price(BigDecimal.ZERO);
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                BigDecimal sumResult = rs.getBigDecimal(1);
                if (sumResult != null) {
                    totalPrice = new Price(sumResult);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error calculating total Room price.", e);
        }
        return totalPrice;
    }

    private Room mapRowToRoom(ResultSet rs) throws SQLException {
        Id<Room> foundRoomId = new Id<>(rs.getInt("id_room"));
        Name name = new Name(rs.getString("name"));
        Difficulty difficulty = Difficulty.valueOf(rs.getString("difficulty").toUpperCase());
        Price price = new Price(rs.getBigDecimal("price"));
        Id<Theme> themeId = new Id<>(rs.getInt("theme_id"));
        return Room.rehydrate(foundRoomId, name, difficulty, price, themeId);
    }
}