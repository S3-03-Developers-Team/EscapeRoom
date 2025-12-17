package org.s3team.player.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.s3team.exceptions.PlayerIsAlreadyInDataBaseException;
import org.s3team.player.dao.PlayerDAO;
import org.s3team.player.model.Email;
import org.s3team.player.model.Player;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PlayerServiceTest {
    private PlayerService playerService;
    private PlayerDAO playerDAO;
    private Player player;
    private Player playerWithId;

    @BeforeEach
    void setUp() {
        playerDAO = mock(PlayerDAO.class);
        playerService = new PlayerService(playerDAO);
        player = Player.create(new Name("Pepito"), new Email("pepito@gmail.com"), true);
        playerWithId = Player.rehydrate(new Id<>(98), new Name("Pepito"), new Email("pepito@gmail.com"), true);
    }


    @Test
    void savePlayerSuccessfullyForTheFirstTime() {

        when(playerDAO.findByEmail("pepito@gmail.com"))
                .thenReturn(Optional.empty());

        playerService.save(player);

        verify(playerDAO, times(1)).save(player);
    }

    @Test
    void savePlayerIsUnsuccessfulWhenEmailIsRepeated() {

        Player existingPlayer = Player.create(
                new Name("OtroNombre"),
                new Email("pepito@gmail.com"),
                true
        );

        when(playerDAO.findByEmail("pepito@gmail.com"))
                .thenReturn(Optional.of(existingPlayer));

        assertThrows(PlayerIsAlreadyInDataBaseException.class, () ->
                playerService.save(player)
        );

        verify(playerDAO, never()).save(any());
    }

    @Test
    void findByIdShouldReturnEmptyWhenPlayerDoesNotExist() {
        Id<Player> id = new Id<>(89);
        when(playerDAO.findById(id)).thenReturn(Optional.empty());

        Optional<Player> result = playerService.findById(id);

        assertFalse(result.isPresent());
        verify(playerDAO, times(1)).findById(id);
    }

    @Test
    void findAllShouldReturnAllPlayers() {
        List<Player> players = Arrays.asList(
                Player.create(new Name("Pepito"), new Email("pepito@gmail.com"), true),
                Player.create(new Name("Juan"), new Email("juan@gmail.com"), false),
                Player.create(new Name("Maria"), new Email("maria@gmail.com"), true)
        );

        when(playerDAO.findAll()).thenReturn(players);

        List<Player> result = playerService.findAll();

        assertEquals(3, result.size());
        verify(playerDAO, times(1)).findAll();
    }

    @Test
    void subscribePlayerShouldSubscribePlayerSuccessfully() {
        Id<Player> playerId = new Id<>(playerWithId.getId().value());
        Player unsubscribedPlayer = Player.create(new Name("Pepito"),
                new Email("pepito@gmail.com"), false);

        Player subscribedPlayer = playerWithId;
        when(playerDAO.findById(playerId)).thenReturn(Optional.of(unsubscribedPlayer));
        when(playerDAO.save(any(Player.class))).thenReturn(subscribedPlayer);
        when(playerDAO.update(any(Player.class))).thenReturn(true);

        boolean result = playerService.subscribePlayer(playerId);

        assertTrue(result);
        verify(playerDAO, times(1)).findById(playerId);
        verify(playerDAO, times(1)).update(argThat(player ->
                playerWithId.getId().equals(playerId) && player.isSubscribed()
        ));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void verifyPlayerIsNotInDataBaseWhenFindByEmail() {
        String emailNotExist = "hola@gmail.com";
        when(playerDAO.findByEmail(emailNotExist)).thenReturn(Optional.empty());

        Optional<Player> result = playerService.findByEmail(emailNotExist);

        verify(playerDAO, times(1)).findByEmail(emailNotExist);

    }

    @Test
    void findByName() {
    }


}