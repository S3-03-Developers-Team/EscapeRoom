package org.s3team.player.dao;

import org.s3team.player.model.Player;
import org.s3team.common.dao.CrudDao;

import java.util.Optional;

public interface PlayerDao extends CrudDao<Player> {
    Optional<Player> findByEmail(String email);

    Optional<Player> findByName(String name);

}
