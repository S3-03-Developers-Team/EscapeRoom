package org.s3team.clue.dao;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.clue.model.Clue;
import org.s3team.clue.model.ClueDescription;
import org.s3team.clue.model.ClueType;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClueDaoImpl implements ClueDao{

    private final Data_Base_Connection db;

    public ClueDaoImpl(Data_Base_Connection db) { this.db = db; }

    @Override
    public Clue save(Clue clue) {
        String sql = "INSERT INTO clue (type,clue_description,price,theme_id,room_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){


            stmt.setString(1, clue.getType().name());
            stmt.setString(2, clue.getDescription().value());
            stmt.setBigDecimal(3, clue.getPrice().value());
            stmt.setInt(4, clue.getThemeId().value());
            stmt.setInt(5, clue.getRoomId().value());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Id<Clue> generatedId = new Id<>(rs.getInt(1));
                return Clue.rehydrate(
                        generatedId,
                        clue.getType(),
                        clue.getDescription(),
                        clue.getPrice(),
                        clue.getThemeId(),
                        clue.getRoomId()
                );
            } else {
                throw new SQLException("No ID returned for clue");
            }
        } catch (SQLException e){
            throw new RuntimeException("Error saving clue", e);
        }
    }

    @Override
    public Optional<Clue> findById(Id<Clue> id) {
        String sql = "SELECT * FROM clue WHERE id_clue = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding clue", e);
        }
    }

    @Override
    public List<Clue> findAll() {
        String sql = "SELECT * FROM clue";
        List<Clue> clues = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clues.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all clues", e);
        }
        return clues;
    }

    @Override
    public boolean update(Clue clue) {
        String sql = "UPDATE clue SET type = ?, clue_description = ?, price = ?, theme_id = ?, room_id = ? WHERE id_clue = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clue.getType().toString());
            stmt.setString(2, clue.getDescription().value());
            stmt.setBigDecimal(3, clue.getPrice().value());
            stmt.setInt(4, clue.getThemeId().value());
            stmt.setInt(5, clue.getRoomId().value());
            stmt.setInt(6, clue.getId().value());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating clue", e);
        }
    }

    @Override
    public boolean delete(Id<Clue> id) {
        String sql = "DELETE FROM clue WHERE id_clue = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting clue", e);
        }
    }



    private Clue mapRow(ResultSet rs) throws SQLException {
        return Clue.rehydrate(
                new Id<>(rs.getInt("id_clue")),
                ClueType.valueOf(rs.getString("type")),
                new ClueDescription(rs.getString("clue_description")),
                new Price(rs.getBigDecimal("price")),
                new Id<>(rs.getInt("theme_id")),
                new Id<>(rs.getInt("room_id"))
        );
    }


    @Override
    public int count() {
        final String sql = "SELECT COUNT(*) FROM clue";
        int count = 0;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error counting clues.", e);
        }
        return count;
    }

    @Override
    public Price calculateTotalPrice() {
        final String sql = "SELECT SUM(price) FROM clue";
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

}
