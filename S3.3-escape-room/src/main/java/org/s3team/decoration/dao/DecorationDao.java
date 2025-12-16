package org.s3team.decoration.dao;

import org.s3team.common.dao.CrudDao;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.decoration.model.Decoration;

import java.util.List;
import java.util.Optional;

public interface DecorationDao extends CrudDao<Decoration> {
    @Override
    default Decoration save(Decoration entity) {
        return null;
    }

    @Override
    default Optional<Decoration> findById(Id<Decoration> id) {
        return Optional.empty();
    }

    @Override
    default List<Decoration> findAll() {
        return List.of();
    }

    @Override
    default boolean update(Decoration entity) {
        return false;
    }

    @Override
    default boolean delete(Id<Decoration> id) {
        return false;
    }


    default int count() {
        return 0;
    }

    default Price calculateTotalPrice() {
        return null;
    }
}
