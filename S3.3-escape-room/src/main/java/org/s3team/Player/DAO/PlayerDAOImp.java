package org.s3team.Player.DAO;

import org.s3team.DataBaseConnection.Data_Base_Connection;
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

    private final Data_Base_Connection dataBaseConnection;

    public PlayerDAOImp(Data_Base_Connection dataBaseConnection) {
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
                    throw new SQLException("No ID returned for player");
                }
            }

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't save to data base", e);

        }
    }

    @Override
    public Optional<Player> findById(Id<Player> id) {
        final String sql = "SELECT * FROM player WHERE id_player=?";

        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id.value());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't find player's Id", e);
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
        final String sql = "SELECT * FROM player";
        List<Player> players = new ArrayList<>();
        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                players.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseConnectionException("Can't find players", e);
        }
        return List.copyOf(players);
    }

    @Override
    public boolean update(Player player) {
        final String sql = "UPDATE player SET name = ?, email = ?, subscribed = ? WHERE id_player = ?";
        if (player.getId() == null) {
            throw new IllegalArgumentException("Cannot update Player: ID is missing.");
        }
        int playerIdValue = player.getId().value();
        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, player.getName().value());
            ps.setString(2, player.getEmail().value());
            ps.setBoolean(3, player.isSubscribed());
            ps.setInt(4, playerIdValue);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't find players", e);
        }
    }

    @Override
    public boolean delete(Id<Player> id) {
        final String sql = "DELETE FROM player WHERE id_player = ? ";

        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id.value());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Couldn't erase player from table", e);
        }
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        final String sql = "SELECT * FROM player WHERE email = ?";

        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't find player's email", e);
        }
    }

    @Override
    public Optional<Player> findByName(String name) {
        final String sql = "SELECT * FROM player WHERE name = ?";

        try (PreparedStatement ps = dataBaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't find player's name", e);
        }
    }
}
