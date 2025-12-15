package org.s3team.common.dao;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    T save(T entity);

    Optional<T> findById(Id<T> id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(Id<T> id);

}

