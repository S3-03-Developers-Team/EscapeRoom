package org.s3team.notification;

import org.s3team.Player.Model.Player;
import org.s3team.Player.Service.PlayerService;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationManagementMenu {
    private final SendNotificationService notificationService;
    private final PlayerService playerService;

    public NotificationManagementMenu(SendNotificationService notificationService, PlayerService playerService) {
        this.notificationService = notificationService;
        this.playerService = playerService;
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
                    System.out.println("--- Subscribe Player ---");
                    int idInput = ConsoleInput.readInt("Enter Player Id: ");
                    Id<Player> playerId = new Id<>(idInput);

                    try {
                        boolean subscribedPlayer = playerService.subscribePlayer(playerId);
                        if (subscribedPlayer) {
                            System.out.println("✅ Player subscribed successfully!");
                        }

                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to subscribe player: " + e.getMessage());
                    }

                }
                case 2 -> {
                    System.out.println("--- Unsubscribe Player ---");
                    int idInput = ConsoleInput.readInt("Enter Player Id: ");
                    Id<Player> playerId = new Id<>(idInput);

                    try {
                        boolean unSubscribedPlayer = playerService.unSubscribePlayer(playerId);
                        if (unSubscribedPlayer) {
                            System.out.println("✅ Player unsubscribed successfully!");
                        }

                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to subscribe player: " + e.getMessage());
                    }


                }
                case 3 -> {
                    System.out.println("--- List all Subscribed Players ---");

                    System.out.println("---------------------------------");

                    List<Player> subscribedPlayers = playerService.findAll().stream()
                            .filter(Player::isSubscribed)
                            .collect(Collectors.toList());

                    subscribedPlayers.forEach(e -> System.out.println(e.toString()));

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
