package org.s3team.decoration.dao;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.common.dao.CrudDao;
import org.s3team.decoration.model.Decoration;
import org.s3team.common.valueobject.Id;
import org.s3team.decoration.model.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecorationDaoImpl implements DecorationDao {

    @Override
    public Decoration save(Decoration decoration) {
        String sql = "INSERT INTO decoration_object (name, material, stock, price, room_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = MySQL_Data_Base_Connection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, decoration.getName());
            pstmt.setString(2, decoration.getMaterial().name()); // Enum to String
            pstmt.setInt(3, decoration.getStock());
            pstmt.setBigDecimal(4, decoration.getPrice());
            pstmt.setInt(5, decoration.getRoomId());

            pstmt.executeUpdate();
            System.out.println("DEBUG: Decoration saved successfully in DB.");

        } catch (SQLException exception) {
            System.err.println("ERROR: Could not save decoration. " + exception.getMessage());
        }
        return decoration;
    }

    @Override
    public Optional<Decoration> findById(Id<Decoration> id) {
        return Optional.empty();
    }

    public List<Decoration> findByRoomId(int roomId) {
        List<Decoration> roomDecorations = new ArrayList<>();
        String sql = "SELECT * FROM decoration_object WHERE room_id = ?";

        try (Connection connection = MySQL_Data_Base_Connection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Decoration decoration = mapResultSetToDecoration(resultSet);
                    roomDecorations.add(decoration);
                }
            }
        } catch (SQLException exception) {
            System.err.println("ERROR: Could not find decorations for room " + roomId + ". " + exception.getMessage());
        }
        return roomDecorations;
    }

    @Override
    public List<Decoration> findAll() {
        List<Decoration> allDecorations = new ArrayList<>();
        String sql = "SELECT * FROM decoration_object";

        try (Connection conn = MySQL_Data_Base_Connection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                allDecorations.add(mapResultSetToDecoration(resultSet));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return allDecorations;
    }

    private Decoration mapResultSetToDecoration(ResultSet resultSet) throws SQLException {
        return new Decoration(
                resultSet.getInt("id_decoration_object"), // Database Column Name
                resultSet.getString("name"),
                Material.valueOf(resultSet.getString("material")), // String to Enum
                resultSet.getInt("stock"),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("room_id")
        );
    }

    @Override public Optional<Decoration> findById(int id) { return Optional.empty(); }
    @Override public boolean update(Decoration entity) { return false; }

    @Override
    public boolean delete(Id<Decoration> id) {
        return false;
    }

}
