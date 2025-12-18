package org.s3team.ticket.dao;

import org.s3team.common.dao.CrudDao;
import org.s3team.common.valueobject.Price;
import org.s3team.ticket.model.Ticket;

public interface TicketDao extends CrudDao<Ticket> {
    Price calculateTotalRevenue();
    int count();
}
