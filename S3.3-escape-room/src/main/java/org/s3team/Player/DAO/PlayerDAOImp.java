package org.s3team.Player.DAO;

import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;

import java.util.List;
import java.util.Optional;

public class PlayerDAOImp implements PlayerDAO {


    @Override
    public Optional<Player> findByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Player> findByName(String nickName) {
        return null;
    }

    @Override
    public void save(Object entity) {

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
}
