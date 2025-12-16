package org.s3team.ticket.dao;


import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.ticket.model.Ticket;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDaoImpl implements TicketDao {

    private final Data_Base_Connection db;

    public TicketDaoImpl(Data_Base_Connection db) {
        this.db = db;
    }

    @Override
    public Ticket save(Ticket ticket){
        String insertSql = "INSERT INTO ticket (total, player_id, room_id) VALUES (?, ?, ?)";
        String selectSql = "SELECT purchase_date FROM ticket WHERE id_ticket = ?";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setBigDecimal(1, ticket.getTotal().value());
                stmt.setInt(2, ticket.getPlayerId().value());
                stmt.setInt(3, ticket.getRoomId().value());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        Id<Ticket> generatedId = new Id<>(rs.getInt(1));

                        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                            selectStmt.setInt(1, generatedId.value());
                            try (ResultSet rs2 = selectStmt.executeQuery()) {
                                if (rs2.next()) {
                                    Timestamp ts = rs2.getTimestamp("purchase_date");
                                    LocalDateTime purchaseDate = ts.toLocalDateTime();

                                    conn.commit();

                                    return Ticket.rehydrate(
                                            generatedId,
                                            purchaseDate,
                                            ticket.getTotal(),
                                            ticket.getPlayerId(),
                                            ticket.getRoomId()
                                    );
                                } else {
                                    conn.rollback();
                                    throw new RuntimeException("Failed to retrieve purchase_date");
                                }
                            }
                        }
                    } else {
                        conn.rollback();
                        throw new RuntimeException("No Ticket ID generated");
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Ticket", e);
        }
    }

    @Override
    public Optional<Ticket> findById(Id<Ticket> id) {
        String sql = "SELECT * FROM ticket WHERE id_ticket = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1,id.value());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding ticket", e);
        }
    }

    @Override
    public List<Ticket> findAll() {
        String sql = "SELECT * FROM ticket";
        List<Ticket> tickets = new ArrayList<>();

        try(Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tickets.add(mapRow(rs));
            }
        } catch (SQLException e){
            throw new RuntimeException("Error finding all tickets", e);
        }
        return tickets;
    }

    @Override
    public boolean update(Ticket ticket) {
        String sql = "UPDATE ticket SET total = ?, player_id = ?, room_id = ? WHERE id_ticket = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, ticket.getTotal().value());
            stmt.setInt(2, ticket.getPlayerId().value());
            stmt.setInt(3, ticket.getRoomId().value());
            stmt.setInt(4, ticket.getId().value());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating ticket", e);
        }
    }

    @Override
    public boolean delete(Id<Ticket> id) {
        String sql = "DELETE FROM ticket WHERE id_ticket = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting ticket", e);
        }
    }

    @Override
    public Price calculateTotalRevenue(){
        String sql = "SELECT COALESCE(SUM(total), 0) AS revenue FROM ticket";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new Price(rs.getBigDecimal("revenue"));
            }
            return new Price(BigDecimal.ZERO);

        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total revenue", e);
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM ticket";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error counting tickets", e);
        }
    }

    private Ticket mapRow(ResultSet rs) throws SQLException {
        return Ticket.rehydrate(
                new Id<>(rs.getInt("id_ticket")),
                rs.getTimestamp("purchase_date").toLocalDateTime(),
                new Price(rs.getBigDecimal("total")),
                new Id<>(rs.getInt("player_id")),
                new Id<>(rs.getInt("room_id"))
        );
    }
}
