package org.s3team.common.dao;

import org.s3team.decoration.model.Decoration;
import org.s3team.common.valueobject.Id;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    T save(T entity);

    Optional<T> findById(Id<T> id);

    List<T> findAll();

    // Métodos pendientes del contrato (puedes dejarlos así por ahora)
    Optional<Decoration> findById(int id);

    boolean update(T entity);

    boolean delete(Id<T> id);

    boolean delete(int id);
}

