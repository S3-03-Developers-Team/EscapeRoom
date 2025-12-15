package org.s3team.notification;

import org.s3team.Player.DAO.PlayerDAO;
import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Model.EventListener;
import org.s3team.Player.Model.Player;
import org.s3team.common.util.ConsoleInput;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationManagementMenu {
    private final SendNotificationService notificationService;
    private final PlayerDAO playerDAO;

    public NotificationManagementMenu(SendNotificationService notificationService, PlayerDAO playerDAO) {
        this.notificationService = notificationService;
        this.playerDAO = playerDAO;
    }

    public void displayNotificationMenu() {
        boolean notificationMenuExit = false;
        while (!notificationMenuExit) {

            System.out.println("\n--- Notification Menu ---");
            System.out.println("1. Subscribe Player");
            System.out.println("2. Unsubscribe Player");
            System.out.println("3. List All Subscribed Players");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            int option = ConsoleInput.readInt("Choose an option (0-4) : ");

            switch (option) {
                case 1 -> {

                }
                case 2 -> {

                }
                case 3 -> {
                    System.out.println("--- List all Subscribed Players ---");

                    System.out.println("---------------------------------");

                    List<Player> subscribedPlayers = playerDAO.findAll().stream()
                            .filter(Player::isSubscribed)
                            .collect(Collectors.toList());

                    subscribedPlayers.forEach(e->e.toString());

                    System.out.println("---------------------------------");

                }
                case 0 -> {
                    System.out.println("Returning to Main Menu...");
                    notificationMenuExit = true;
                }
                default -> System.out.println("❌ Invalid option. Please choose a number from 0 to 4.");

            }

        }
    }
}
