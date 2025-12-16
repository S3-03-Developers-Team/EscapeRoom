package org.s3team.Player.Service;

import org.s3team.Player.DAO.PlayerDAO;
import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;

import java.util.List;
import java.util.Optional;

public class PlayerService {
    private PlayerDAO playerDAO;


    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public Player save(Player player) {
        return playerDAO.save(player);
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



