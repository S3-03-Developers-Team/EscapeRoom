package org.s3team.notification;

import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Model.EventListener;
import org.s3team.Player.Model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SendNotification {
    private PlayerDAOImp playerDAOImp;
    Map<String, List<EventListener>> subscribers = new HashMap<>();

    public SendNotification(String... operations) {
        for (String operation : operations) {
            this.subscribers.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> users = subscribers.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = subscribers.get(eventType);
        users.remove(listener);
    }

    public void notifyUpdate(String eventType, String message) {
        List<EventListener> users = subscribers.get(eventType);
        for (EventListener listener : users) {
            listener.notification(message);
        }
    }

    public void sendNotificationToSubscribers(String message){
        List<Player> allPlayers = playerDAOImp.findAll();
        allPlayers.stream()
                .filter(Player::isSubscribed)
                .collect(Collectors.toSet());
    }

}
