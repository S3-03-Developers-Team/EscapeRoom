package org.s3team.ticket.service;

import org.s3team.Exceptions.PlayerNotFoundException;
import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.Player.DAO.PlayerDAO;
import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.model.Room;
import org.s3team.ticket.dao.TicketDao;
import org.s3team.ticket.model.Ticket;

import java.util.List;
import java.util.Optional;

public class TicketService {

    private final TicketDao ticketDao;
    private final RoomDAO roomDao;
    private final PlayerDAO playerDao;

    public TicketService(TicketDao ticketDao, RoomDAO roomDao, PlayerDAO playerDao){
        this.ticketDao = ticketDao;
        this.roomDao = roomDao;
        this.playerDao = playerDao;
    }

    public Ticket createTicket(Price total,
                               Id<Player> playerId, Id<Room> roomId) {

        playerDao.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        roomDao.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));

        Ticket ticket = Ticket.createNew(total, playerId, roomId);

        return ticketDao.save(ticket);
    }

    public Optional<Ticket> getTicketById(Id<Ticket> id) {
        return ticketDao.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }

    public boolean updateTicket(Ticket ticket) {

        playerDao.findById(ticket.getPlayerId()).orElseThrow(() -> new PlayerNotFoundException(ticket.getPlayerId()));
        roomDao.findById(ticket.getRoomId()).orElseThrow(() -> new RoomNotFoundException(ticket.getRoomId()));

        return ticketDao.update(ticket);
    }

    public boolean deleteTicket(Id<Ticket> id) {
        return ticketDao.delete(id);
    }

    public Price getTotalRevenue() {
        return ticketDao.calculateTotalRevenue();
    }

    public int countTickets() {
        return ticketDao.count();
    }
}

