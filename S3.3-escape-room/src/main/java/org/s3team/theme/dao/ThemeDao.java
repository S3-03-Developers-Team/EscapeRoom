package org.s3team.theme.dao;

import org.s3team.Exceptions.ThemeNotFoundException;
import org.s3team.common.dao.CrudDao;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.theme.model.Theme;

import java.util.Optional;

public interface ThemeDao extends CrudDao<Theme> {

    Optional<Theme> findByName(Name name);
    boolean deleteByName(Name name);
    default Theme getById(Id<Theme> id){
        return findById(id).orElseThrow(() -> new ThemeNotFoundException(id ));
    }
    default Theme getByName(Name name){
        return findByName(name).orElseThrow(() -> new ThemeNotFoundException(name));
    }
}
