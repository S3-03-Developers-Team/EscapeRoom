package org.s3team.Player.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.s3team.Exceptions.DataBaseConnectionException;
import org.s3team.Player.DAO.PlayerDAO;
import org.s3team.Player.Model.Email;
import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Name;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class PlayerServiceTest {
    private PlayerService playerService;
    private PlayerDAO playerDAO;
    private Player player;

    @BeforeEach
    void setUp() {
        playerDAO = mock(PlayerDAO.class);
        playerService = new PlayerService(playerDAO);
        player = Player.create(new Name("Pepito"), new Email("pepito@gmail.com"), true);

    }


    @Test
    void savePLayerSuccessfullyForTheFirstTime() {
        when(playerDAO.findByName(player.getName().value())).thenReturn(Optional.empty());
        when(playerDAO.findByEmail(player.getEmail().value())).thenReturn(Optional.empty());

        playerService.save(player);

        verify(playerDAO,times(1)).save(player);
    }

    @Test
    void savePlayerIsUnsuccessfulWhenPlayerIsRepeated(){
        //when(playerDAO.findByName(player.getName().value())).thenReturn(Optional.of(Player.create(player.getName(), player.getEmail(), true)));
        when(playerDAO.findByEmail(player.getEmail().value())).thenReturn(Optional.of(Player.create(player.getName(), player.getEmail(), true)));

        assertThrows(DataBaseConnectionException.class, () -> playerService.save(player));
        verify(playerDAO,times(0)).save(player);
    }



    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByName() {
    }

    @Test
    void subscribePlayer() {
    }

    @Test
    void unSubscribePlayer() {
    }

}