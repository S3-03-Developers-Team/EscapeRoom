package org.s3team.decoration.dao;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.decoration.model.Decoration;
import org.s3team.decoration.model.Material;
import org.s3team.room.model.Room;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecorationDaoImpl implements DecorationDao {

    private final Data_Base_Connection db;

    public DecorationDaoImpl(Data_Base_Connection db) {
        this.db = db;
    }

    @Override
    public Decoration save(Decoration decoration) {
        String sql = "INSERT INTO decoration_object (name, material, stock, price, room_id) VALUES (?, ?, ?, ?, ?)";

        Id<Decoration> generatedId = null;

        try (Connection connection = db.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, decoration.getName().value());               // Name -> String
            pstmt.setString(2, decoration.getMaterial().name());           // Material (Enum) -> String
            pstmt.setInt(3, decoration.getStock());                        // Integer
            pstmt.setBigDecimal(4, decoration.getPrice().value());         // Price -> BigDecimal
            pstmt.setInt(5, decoration.getRoomId().value());               // Id<Room> -> Integer

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = new Id<>(rs.getInt(1));
                } else {
                    throw new SQLException("Database did not return a generated ID for the new Decoration.");
                }
            }

            System.out.println("DEBUG: Decoration saved successfully in DB with ID: " + generatedId.value());

        } catch (SQLException exception) {
            System.err.println("ERROR: Could not save decoration. " + exception.getMessage());
            throw new RuntimeException("Error saving Decoration object to database.", exception);
        }
        return Decoration.rehydrate(
                generatedId,
                decoration.getName(),
                decoration.getMaterial(),
                decoration.getStock(),
                decoration.getPrice(),
                decoration.getRoomId()
        );
    }

    @Override
    public Optional<Decoration> findById(Id<Decoration> id) {
        String sql = "SELECT * FROM decoration_object WHERE id_decoration_object = ?";
        if (id == null) {
            return Optional.empty();
        }

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Error finding Decoration by ID: " + id.value());
            throw new RuntimeException("Database error finding Decoration by ID.", e);
        }
    }

    public List<Decoration> findByRoomId(int roomId) {
        List<Decoration> roomDecorations = new ArrayList<>();
        String sql = "SELECT * FROM decoration_object WHERE room_id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Decoration decoration = mapRow(resultSet);
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

        try (Connection connection = db.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                allDecorations.add(mapRow(resultSet));
            }

        } /*catch (SQLException exception) {
            exception.printStackTrace();
        }*/
        catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Error during findAll execution.", exception);
        }
        return allDecorations;
    }

    private Decoration mapRow(ResultSet rs) throws SQLException {
        Id<Decoration> decorationId = new Id<>(rs.getInt("id_decoration_object"));
        Name name = new Name(rs.getString("name"));
        Material material = Material.valueOf(rs.getString("material"));
        Integer stock = rs.getInt("stock");
        Price price = new Price(rs.getBigDecimal("price"));
        Id<Room> roomId = new Id<>(rs.getInt("room_id"));

        return Decoration.rehydrate(
                decorationId,
                name,
                material,
                stock,
                price,
                roomId
        );
    }

    @Override
    public boolean update(Decoration decoration) {
            String sql = "UPDATE decoration_object SET name = ?, material = ?, stock = ?, price = ?, room_id = ? " +
                    "WHERE id_decoration_object = ?";

            Id<Decoration> decorationId = decoration.getDecorationId();
            if (decorationId == null) {
                System.err.println("ERROR: Cannot update Decoration entity without a valid ID.");
                return false;
            }

            try (Connection connection = db.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {

                pstmt.setString(1, decoration.getName().value());

                pstmt.setString(2, decoration.getMaterial().name());

                pstmt.setInt(3, decoration.getStock());

                pstmt.setBigDecimal(4, decoration.getPrice().value());

                pstmt.setInt(5, decoration.getRoomId().value());

                pstmt.setInt(6, decorationId.value());

                int affectedRows = pstmt.executeUpdate();

                System.out.println("DEBUG: Decoration ID " + decorationId.value() + " updated. Rows affected: " + affectedRows);

                return affectedRows > 0;

            } catch (SQLException e) {
                System.err.println("ERROR: Database error updating Decoration ID: " + decorationId.value());
                throw new RuntimeException("Error updating Decoration object in database.", e);
            }
        }
        @Override
        public boolean delete (Id < Decoration > id) {
            String sql = "DELETE FROM decoration_object WHERE id_decoration_object = ?";

            if (id == null) {
                System.err.println("ERROR: Cannot delete Decoration entity, ID is null.");
                return false;
            }

            try (Connection connection = db.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {

                pstmt.setInt(1, id.value());

                int affectedRows = pstmt.executeUpdate();

                System.out.println("DEBUG: Decoration ID " + id.value() + " deletion attempted. Rows affected: " + affectedRows);

                return affectedRows > 0;

            } catch (SQLException e) {
                System.err.println("ERROR: Database error deleting Decoration ID: " + id.value());
                throw new RuntimeException("Error deleting Decoration object from database.", e);
            }
        }


        @Override
        public Price calculateTotalPrice() {
            final String sql = "SELECT SUM(stock * price) FROM decoration_object";
            BigDecimal totalPriceValue = BigDecimal.ZERO;

            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    BigDecimal sumResult = rs.getBigDecimal(1);
                    if (sumResult != null) {
                        totalPriceValue = sumResult;
                    }
                }

                System.out.println("DEBUG: Total Decoration value calculated: " + totalPriceValue);

            } catch (SQLException e) {
                System.err.println("ERROR: Database error calculating total price for Decorations. " + e.getMessage());
                throw new RuntimeException("Database error calculating total price for Decorations.", e);
            }

            return new Price(totalPriceValue);
        }

    @Override
    public int count() {
        String sql = "SELECT SUM(stock) FROM decoration_object";

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            System.err.println("ERROR: Database error while calculating total stock.");
            throw new RuntimeException("Database error calculating total stock.", e);
        }
    }
}
