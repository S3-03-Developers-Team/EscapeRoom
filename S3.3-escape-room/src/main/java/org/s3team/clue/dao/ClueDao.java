package org.s3team.clue.dao;

import org.s3team.clue.model.Clue;
import org.s3team.common.dao.CrudDao;
import org.s3team.common.valueobject.Price;

public interface ClueDao extends CrudDao<Clue> {
    int count();

    Price calculateTotalPrice();
}
