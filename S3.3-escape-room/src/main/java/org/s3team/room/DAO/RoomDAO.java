package org.s3team.room.DAO;

import org.s3team.common.dao.CrudDao;
import org.s3team.common.valueobject.*;
import org.s3team.room.model.Room;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: RoomDAO
 * Package: room
 * Description:
 * Author: Rong Jiang
 * Create:10/12/2025 - 21:59
 * Version:v1.0
 *
 */
public interface RoomDAO extends CrudDao<Room> {
    @Override
    default Room save(Room entity) {
        return null;
    }

    @Override
    default Optional<Room> findById(Id<Room> id) {
        return Optional.empty();
    }

    @Override
    default List<Room> findAll() {
        return List.of();
    }

    @Override
    default boolean update(Room entity) {
        return false;
    }

    @Override
    default boolean delete(Id<Room> id) {
        return false;
    }

    default int count() {
        return 0;
    }

    default Price calculateTotalPrice() {
        return null;
    }
}
