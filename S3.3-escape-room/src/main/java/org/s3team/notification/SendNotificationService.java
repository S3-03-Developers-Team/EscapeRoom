package org.s3team.notification;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Model.EventListener;
import org.s3team.Player.Model.Player;
import java.util.List;

import java.util.stream.Collectors;

public class SendNotificationService {
    private PlayerDAOImp playerDAOImp;
    private final Data_Base_Connection dataBaseConnection= MySQL_Data_Base_Connection.getInstance();;

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
