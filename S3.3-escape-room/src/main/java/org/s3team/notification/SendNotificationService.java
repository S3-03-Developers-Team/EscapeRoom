package org.s3team.notification;

import org.s3team.dataBaseConnection.DataBaseConnection;
import org.s3team.dataBaseConnection.MySQLDataBaseConnection;
import org.s3team.player.dao.PlayerDAOImp;
import org.s3team.player.model.EventListener;
import org.s3team.player.model.Player;
import java.util.List;

import java.util.stream.Collectors;

public class SendNotificationService {
    private PlayerDAOImp playerDAOImp;
    private final DataBaseConnection dataBaseConnection= MySQLDataBaseConnection.getInstance();;

   public SendNotificationService(){
       this.playerDAOImp = new PlayerDAOImp(dataBaseConnection);
   }

    public void sendNotificationToSubscribers(String message) {
        List<Player> allPlayers = playerDAOImp.findAll();
        List<EventListener> subscribed = allPlayers.stream()
                .filter(Player::isSubscribed)
                .collect(Collectors.toList());
        subscribed.stream().forEach(e->e.notification(message));

    }

}
