package org.s3team.Player.DAO;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.Exceptions.DataBaseConnectionException;
import org.s3team.Player.Model.Player;
import org.s3team.clue.model.Clue;
import org.s3team.common.valueobject.Id;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


        return player;
    }

    @Override
    public Optional<Player> findById(Id id) {
        String sql = "SELECT * FROM player WHERE id_player=?";

        try(){

        }catch(SQLException e){
            throw new DataBaseConnectionException("Can't find player's Id", e);
        }
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public boolean update(Player entity) {
        return false;
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
