package org.s3team.Player.DAO;

import org.s3team.Player.Model.Player;
import org.s3team.common.dao.CrudDao;

import java.util.Optional;

public interface PlayerDAO extends CrudDao<Player> {
    Optional<Player> findByEmail(String email);

    Optional<Player> findByName(String name);

}
