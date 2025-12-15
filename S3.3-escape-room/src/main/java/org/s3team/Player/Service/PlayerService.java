package org.s3team.Player.Service;

import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Model.Player;
import org.s3team.common.valueobject.Id;

import java.util.List;
import java.util.Optional;

public class PlayerService {
    private PlayerDAOImp playerDAOImp;


    public PlayerService(PlayerDAOImp playerDAOImp) {
        this.playerDAOImp = playerDAOImp;
    }

    public Player save(Player player) {
        return playerDAOImp.save(player);
    }

    public Optional<Player> findById(Id<Player> id) {
        return playerDAOImp.findById(id);
    }

    public List<Player> findAll() {
        return playerDAOImp.findAll();
    }

    public boolean update(Player player) {
        if (playerDAOImp.findById(player.getId()).isEmpty()) {
            return false;
        }
        return playerDAOImp.update(player);
    }

    public boolean delete(Id<Player> id) {
        return playerDAOImp.delete(id);
    }

    public Optional<Player> findByEmail(String email) {
        return playerDAOImp.findByEmail(email);
    }

    public Optional<Player> findByName(String name) {
        return playerDAOImp.findByName(name);
    }

    public boolean subscribePlayer(Id<Player> playerId) {

        Player playerToUpdate = playerDAOImp.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId.value()));

        Player playerToUpdateSubscription = Player.rehydrate(playerToUpdate.getId(), playerToUpdate.getName(), playerToUpdate.getEmail(), true);

        return playerDAOImp.update(playerToUpdateSubscription);
    }

    public boolean unSubscribePlayer(Id<Player> playerId) {

        Player playerToUpdate = playerDAOImp.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId.value()));

        Player playerToUpdateSubscription = Player.rehydrate(playerToUpdate.getId(), playerToUpdate.getName(), playerToUpdate.getEmail(), false);

        return playerDAOImp.update(playerToUpdateSubscription);
    }
}



