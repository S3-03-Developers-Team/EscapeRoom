package org.s3team.Player.menu;

import org.s3team.Exceptions.PlayerNotFoundException;
import org.s3team.Player.Model.Email;
import org.s3team.Player.Model.Player;
import org.s3team.Player.Service.PlayerService;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;

import java.util.Optional;

public class PlayerManagementMenu {
    private final PlayerService playerService;


    public PlayerManagementMenu(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void displayPlayerMenu() {
        boolean playerMenuExit = false;

        while (!playerMenuExit) {

            System.out.println("\n--- Player Menu ---");
            System.out.println("1. Add New Player");
            System.out.println("2. Remove Player");
            System.out.println("3. Search Player");
            System.out.println("4. List All Players");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            int option = ConsoleInput.readInt("Choose an option (0-4) : ");

            switch (option) {
                case 1 -> {
                    System.out.println("--- Adding New Player ---");

                    String nameInput = ConsoleInput.readLine("Enter Player Name: ");
                    Name name = new Name(nameInput);


                    String email = ConsoleInput.readLine("Enter Player email: ");
                    Email emailPlayer = new Email(email);

                    boolean subscribed = ConsoleInput.readBoolean("Subscription to events? y/n: ");


                    try {
                        Player player = Player.create(name, emailPlayer, subscribed);
                        Player savedPlayer = playerService.save(player);
                        System.out.println("✅ Player added successfully!");
                        System.out.println(savedPlayer);

                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to add player: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("--- Remove Player ---");

                    int playerIdValue = ConsoleInput.readInt("Enter the ID of the Player to Delete: ");
                    Id<Player> playerId = new Id<>(playerIdValue);

                    String confirmation = ConsoleInput.readLine("⚠️ WARNING: This action is permanent. Confirm deletion of Room ID " + playerIdValue + " (type YES to proceed): ");

                    if (!"YES".equalsIgnoreCase(confirmation.trim())) {
                        System.out.println("❌ Deletion cancelled by user.");
                        break; // Exit the case
                    }

                    try {
                        // Call the Service layer to perform the deletion
                        playerService.delete(playerId);

                        System.out.println("✅ Player with ID " + playerId + " deleted successfully.");

                    } catch (PlayerNotFoundException e) {

                        System.err.println("❌ Failed to delete player: " + e.getMessage());
                        System.err.println("⚠️ Player ID " + playerId + " not found. No action taken.");
                    } catch (RuntimeException e) {
                        // Catch other exceptions (e.g., Referential Integrity violation)
                        System.err.println("❌ Failed to delete player due to dependencies: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("--- Searching for Player ---");

                    String namePlayer = ConsoleInput.readLine("Enter Player Name: ");


                    try {

                        Optional<Player> foundPlayer = playerService.findByName(namePlayer);

                        System.out.println("\n✅ Player Found:");
                        System.out.println("---------------------------------");
                        System.out.println(foundPlayer.toString());
                        System.out.println("---------------------------------");


                    } catch (PlayerNotFoundException e) {
                        System.err.println("❌ Failed to find player: " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred during searching: " + e.getMessage());
                    }
                }

                case 4 -> {
                    System.out.println("--- List all Players ---");

                    System.out.println("---------------------------------");

                    playerService.findAll().forEach(e -> System.out.println(e.toString()));

                    System.out.println("---------------------------------");

                }

                case 0 -> {
                    System.out.println("Returning to Main Menu...");
                    playerMenuExit = true;
                }
                default ->
                    System.out.println("❌ Invalid option. Please choose a number from 0 to 4.");

            }
        }
    }
}
