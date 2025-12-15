package org.s3team.decoration.dao;

import org.s3team.common.dao.CrudDao;
import org.s3team.decoration.model.Decoration;

import java.util.List;
import java.util.Optional;

public interface DecorationDao extends CrudDao<Decoration> {
    Optional<Decoration> findById(int id);

    List<Decoration> findByRoomId(int roomId);

    boolean updateRoom(int decorationId, int newRoomId);

    boolean delete(int id);

}
