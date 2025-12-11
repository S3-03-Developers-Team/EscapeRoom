package org.s3team.Player.DAO;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.Exceptions.DataBaseConnectionException;
import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;
import org.s3team.room.model.Room;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlayerDAOImp implements PlayerDAO<Player, Integer> {

    private final Data_Base_Connection dataBaseConnection;

    public PlayerDAOImp() {
        try {
            this.dataBaseConnection = MySQL_Data_Base_Connection.getInstance();

        } catch (SQLException e) {
            throw new DataBaseConnectionException("Can't connect to DB", e);

        }
    }


    @Override

    public Player save(Player player) throws SQLException {
        dataBaseConnection.openConnection();

        try {

            PreparedStatement statement = dataBaseConnection.getConnection().prepareStatement("INSERT INTO player(name, email, subscribed) VALUES(?, ?, ?)");
            statement.setString(1, player.getName().toString());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw e;

        } finally {
            dataBaseConnection.closeConnection();
        }


        return player;

    }

    @Override
    public Optional findById(Id id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public boolean update(Object entity) {
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
