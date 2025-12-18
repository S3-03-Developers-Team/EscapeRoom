package org.s3team.player.service;

import org.s3team.exceptions.DataBaseConnectionException;
import org.s3team.exceptions.PlayerIsAlreadyInDataBaseException;
import org.s3team.player.dao.PlayerDAO;
import org.s3team.player.model.Player;
import org.s3team.common.valueobject.Id;

import java.util.List;
import java.util.Optional;

public class PlayerService {
    private PlayerDAO playerDAO;


    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public Player save(Player player) {
        try {
            if (player.getId() != null) {
                throw new IllegalArgumentException("Cannot save existing player. Use update() instead.");
            }

            playerDAO.findByEmail(player.getEmail().value())
                    .ifPresent(existing -> {
                        throw new PlayerIsAlreadyInDataBaseException(
                                "Player with email '" + player.getEmail() + "' already exists"
                        );
                    });

            return playerDAO.save(player);

        } catch (PlayerIsAlreadyInDataBaseException | IllegalArgumentException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new DataBaseConnectionException("Database error", e);
        }
    }

    public Optional<Player> findById(Id<Player> id) {
        return playerDAO.findById(id);
    }

    public List<Player> findAll() {
        return playerDAO.findAll();
    }

    public boolean update(Player player) {
        if (playerDAO.findById(player.getId()).isEmpty()) {
            return false;
        }
        return playerDAO.update(player);
    }

    public boolean delete(Id<Player> id) {
        return playerDAO.delete(id);
    }

    public Optional<Player> findByEmail(String email) {
        return playerDAO.findByEmail(email);
    }

    public Optional<Player> findByName(String name) {
        return playerDAO.findByName(name);
    }

    public boolean subscribePlayer(Id<Player> playerId) {

        Player playerToUpdate = playerDAO.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId.value()));

        Player playerToUpdateSubscription = Player.rehydrate(playerToUpdate.getId(), playerToUpdate.getName(), playerToUpdate.getEmail(), true);

        return playerDAO.update(playerToUpdateSubscription);
    }

    public boolean unSubscribePlayer(Id<Player> playerId) {

        Player playerToUpdate = playerDAO.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId.value()));

        Player playerToUpdateSubscription = Player.rehydrate(playerToUpdate.getId(), playerToUpdate.getName(), playerToUpdate.getEmail(), false);

        return playerDAO.update(playerToUpdateSubscription);
    }
}



