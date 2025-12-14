package org.s3team.ticket.dao;


import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.ticket.model.Ticket;

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
        String sql = "INSERT INTO ticket (total, player_id, room_id) VALUES (?, ?, ?)";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setBigDecimal(1, ticket.getTotal().value());
            stmt.setInt(2, ticket.getPlayerId().value());
            stmt.setInt(3, ticket.getRoomId().value());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Id<Ticket> generatedId = new Id<>(rs.getInt(1));

                Timestamp ts = rs.getTimestamp("purchase_date");
                LocalDateTime purchaseDate = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();

                return Ticket.rehydrate(
                        generatedId,
                        purchaseDate,
                        ticket.getTotal(),
                        ticket.getPlayerId(),
                        ticket.getRoomId()
                );
            } else {
                throw new RuntimeException("No Ticket ID generated");
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
        String sql = "SELECT * FROM clue";
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
