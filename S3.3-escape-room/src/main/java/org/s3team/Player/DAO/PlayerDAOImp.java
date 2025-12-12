package org.s3team.Player.DAO;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.Exceptions.DataBaseConnectionException;
import org.s3team.Player.Model.Email;
import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDAOImp implements PlayerDAO {

    private final MySQL_Data_Base_Connection dataBaseConnection;

    public PlayerDAOImp() {
        try {
            this.dataBaseConnection = MySQL_Data_Base_Connection.getInstance();

        } catch (RuntimeException e) {
            throw new DataBaseConnectionException("Can't connect to DB", e);

        }
    }


    @Override
    public Player save(Player player) {

        final String sql = "INSERT INTO player(name, email, subscribed) VALUES(?, ?, ?)";

        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, player.getName().toString());
            ps.setString(2, player.getEmail().toString());
            ps.setBoolean(3, player.isSubscribed());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Database error: Room creation failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Id<Player> generatedId = new Id<>(generatedKeys.getInt(1));
                    return Player.rehydrate(
                            generatedId,
                            player.getName(),
                            player.getEmail(),
                            player.isSubscribed()
                    );
                } else {
                    throw new SQLException("No ID returned for clue");
                }
            }

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't save to data base", e);

        } finally {
            dataBaseConnection.closeConnection();
        }
    }

    @Override
    public Optional<Player> findById(Id id) {
        String sql = "SELECT * FROM player WHERE id_player=?";

        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id.value());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't find player's Id", e);
        } finally {
            dataBaseConnection.closeConnection();
        }
    }

    private Player mapRow(ResultSet rs) throws SQLException {
        return Player.rehydrate(
                new Id<>(rs.getInt("id_player")),
                new Name(rs.getString("name")),
                new Email(rs.getString("email")),
                rs.getBoolean("subscribed")
        );

    }

    @Override
    public List<Player> findAll() {
        String sql = "SELECT * FROM player";
        List<Player> players = new ArrayList<>();
        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                players.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't find players", e);
        } finally {
            dataBaseConnection.closeConnection();
        }
        return List.copyOf(players);
    }

    @Override
    public boolean update(Player player) {
        String sql = "UPDATE player SET name = ?, email = ?, subscribed = ? ";
        if (player.getId() == null) {
            throw new IllegalArgumentException("Cannot update Player: ID is missing.");
        }
        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, player.getName().value());
            ps.setString(2, player.getEmail().value());
            ps.setBoolean(3, player.isSubscribed());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't find players", e);
        } finally {
            dataBaseConnection.closeConnection();
        }
    }


    @Override
    public boolean delete(Id id) {
        return false;
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Player> findByName(String nickName) {
        return null;
    }
}
