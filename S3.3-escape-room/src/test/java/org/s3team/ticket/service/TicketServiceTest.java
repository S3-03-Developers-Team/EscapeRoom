package org.s3team.ticket.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.exceptions.PlayerNotFoundException;
import org.s3team.exceptions.RoomNotFoundException;
import org.s3team.player.dao.PlayerDAO;
import org.s3team.player.model.Email;
import org.s3team.player.model.Player;
import org.s3team.room.dao.RoomDAO;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;
import org.s3team.theme.model.Theme;
import org.s3team.ticket.dao.TicketDao;
import org.s3team.ticket.model.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketDao ticketDao;

    @Mock
    private RoomDAO roomDao;

    @Mock
    private PlayerDAO playerDao;

    @InjectMocks
    private TicketService ticketService;

    private Id<Player> playerId;
    private Id<Room> roomId;
    private Id<Theme> themeId;

    private Player player;
    private Room room;
    private Ticket ticket;

    @BeforeEach
    void setup() {
        playerId = new Id<>(1);
        roomId = new Id<>(2);
        themeId = new Id<>(3);


        player = Player.rehydrate(playerId, new Name("Player"), new Email("player@test.com"), true);
        room = Room.rehydrate(roomId, new Name("Room"), Difficulty.EASY, new Price(BigDecimal.valueOf(20)), themeId);
        ticket = Ticket.createNew(new Price(BigDecimal.valueOf(5)),playerId,roomId);
    }

    @Test
    void createTicket_ok() {
        Price total = new Price(BigDecimal.valueOf(50));

        when(playerDao.findById(playerId)).thenReturn(Optional.of(player));
        when(roomDao.findById(roomId)).thenReturn(Optional.of(room));
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket ticket = ticketService.createTicket(total, playerId, roomId);

        assertEquals(playerId, ticket.getPlayerId());
        assertEquals(roomId, ticket.getRoomId());
        assertEquals(total, ticket.getTotal());

        verify(playerDao).findById(playerId);
        verify(roomDao).findById(roomId);
        verify(ticketDao).save(any(Ticket.class));
    }

    @Test
    void createTicket_playerNotFound(){
        when(playerDao.findById(playerId)).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class,
                () -> ticketService.createTicket(
                        new Price(BigDecimal.TEN),
                        playerId,
                        roomId
                ));

        verify(playerDao).findById(playerId);
        verify(roomDao, never()).findById(any());
        verify(ticketDao, never()).save(any());
    }

    @Test
    void createTicket_roomNotFound() {
        when(playerDao.findById(playerId)).thenReturn(Optional.of(player));
        when(roomDao.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class,
                () -> ticketService.createTicket(
                        new Price(BigDecimal.TEN),
                        playerId,
                        roomId
                ));

        verify(playerDao).findById(playerId);
        verify(roomDao).findById(roomId);
        verify(ticketDao, never()).save(any());
    }

    @Test
    void getTicketById_found() {

        when(ticketDao.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        Optional<Ticket> result = ticketService.getTicketById(ticket.getId());

        assertTrue(result.isPresent());
        assertEquals(ticket, result.get());
        verify(ticketDao).findById(ticket.getId());
    }

    @Test
    void getAllTickets_ok() {
        Ticket ticket2 = Ticket.createNew(new Price(BigDecimal.valueOf(30)), playerId, roomId);
        when(ticketDao.findAll()).thenReturn(List.of(
                ticket,
                ticket2
        ));

        List<Ticket> tickets = ticketService.getAllTickets();

        assertEquals(2, tickets.size());
        verify(ticketDao).findAll();
    }

    @Test
    void updateTicket_ok() {
        Ticket updatedTicket = Ticket.rehydrate(ticket.getId(), ticket.getPurchaseDate(), new Price(BigDecimal.valueOf(50)), playerId, roomId);

        when(playerDao.findById(playerId)).thenReturn(Optional.of(player));
        when(roomDao.findById(roomId)).thenReturn(Optional.of(room));
        when(ticketDao.update(updatedTicket)).thenReturn(true);

        boolean result = ticketService.updateTicket(updatedTicket);

        assertTrue(result);
        verify(playerDao).findById(playerId);
        verify(roomDao).findById(roomId);
        verify(ticketDao).update(updatedTicket);
    }

    @Test
    void deleteTicket_ok() {

        when(ticketDao.delete(ticket.getId())).thenReturn(true);

        boolean result = ticketService.deleteTicket(ticket.getId());

        assertTrue(result);
        verify(ticketDao).delete(ticket.getId());
    }

    @Test
    void getTotalRevenue_ok() {
        Price totalRevenue = new Price(BigDecimal.valueOf(500));

        when(ticketDao.calculateTotalRevenue()).thenReturn(totalRevenue);

        Price result = ticketService.getTotalRevenue();

        assertEquals(totalRevenue, result);
        verify(ticketDao).calculateTotalRevenue();
    }

    @Test
    void countTickets_ok() {
        when(ticketDao.count()).thenReturn(3);

        int count = ticketService.countTickets();

        assertEquals(3, count);
        verify(ticketDao).count();
    }
}
