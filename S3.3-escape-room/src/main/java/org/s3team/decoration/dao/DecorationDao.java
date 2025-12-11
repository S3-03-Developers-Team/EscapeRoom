package org.s3team.decoration.dao;

import org.s3team.common.dao.CrudDao;
import org.s3team.decoration.model.Decoration;

import java.util.Optional;

public interface DecorationDao extends CrudDao<Decoration> {
    Optional<Decoration> findById(int id);
}
