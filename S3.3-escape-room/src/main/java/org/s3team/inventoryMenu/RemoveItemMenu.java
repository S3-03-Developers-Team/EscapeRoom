package org.s3team.inventoryMenu;

import org.s3team.Exceptions.ClueNotFoundException;
import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.clue.model.Clue;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;
import org.s3team.decoration.model.Decoration;
import org.s3team.inventoryService.InventoryManagementService;
import org.s3team.room.model.Room;


/**
 * ClassName: RemoveItemMenu
 * Package: org.s3team.inventoryMenu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 21:39
 * Version:v1.0
 *
 */
public class RemoveItemMenu {
    private final InventoryManagementService inventoryManagementService;

    public RemoveItemMenu(InventoryManagementService inventoryManagementService) {
        this.inventoryManagementService = inventoryManagementService;
    }
    public void removeItem() {
        boolean inventoryMenuExit = false;

        while (!inventoryMenuExit) {

            System.out.println("\n--- Remove Items ---");
            System.out.println("1. Delete Room");
            System.out.println("2. Delete Clue");
            System.out.println("3. Delete Decoration");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            int option = ConsoleInput.readInt("Choose an option (0-3) : ");

            switch (option) {
                case 1 -> { // Option for Deleting a Room
                    System.out.println("--- 🗑️ Deleting Existing Room ---");

                    // 1. Get Room ID
                    // Ask for the ID of the Room to be deleted
                    int roomIdValue = ConsoleInput.readInt("Enter the ID of the Room to Delete: ");
                    Id<Room> roomId = new Id<>(roomIdValue);

                    // 2. Confirmation (Best Practice for destructive actions)
                    String confirmation = ConsoleInput.readLine("⚠️ WARNING: This action is permanent. Confirm deletion of Room ID " + roomIdValue + " (type YES to proceed): ");

                    if (!"YES".equalsIgnoreCase(confirmation.trim())) {
                        System.out.println("❌ Deletion cancelled by user.");
                        break; // Exit the case
                    }

                    try {
                        // Call the Service layer to perform the deletion
                        inventoryManagementService.deleteRoom(roomId);
                        // 假设 deleteRoom 成功时不返回 boolean，而是在失败时抛出异常 (RoomNotFound, Referential Integrity)

                        System.out.println("✅ Room with ID " + roomIdValue + " deleted successfully.");

                    } catch (RoomNotFoundException e) {
                        // Catch explicit exception if the room ID doesn't exist
                        System.err.println("❌ Failed to delete room: " + e.getMessage());
                        System.err.println("⚠️ Room ID " + roomIdValue + " not found. No action taken.");
                    } catch (RuntimeException e) {
                        // Catch other exceptions (e.g., Referential Integrity violation)
                        System.err.println("❌ Failed to delete room due to dependencies: " + e.getMessage());
                    }
                }
                case 2 -> { // Option for Deleting a Clue
                    System.out.println("--- 🗑️ Deleting Existing Clue ---");

                    // 1. Get Clue ID
                    // Ask for the ID of the Clue to be deleted
                    int clueIdValue = ConsoleInput.readInt("Enter the ID of the Clue to Delete: ");
                    Id<Clue> clueId = new Id<>(clueIdValue);

                    // 2. Confirmation (Best Practice for destructive actions)
                    String confirmation = ConsoleInput.readLine("⚠️ WARNING: This action is permanent. Confirm deletion of Clue ID " + clueIdValue + " (type YES to proceed): ");

                    if (!"YES".equalsIgnoreCase(confirmation.trim())) {
                        System.out.println("❌ Deletion cancelled by user.");
                        break; // Exit the case
                    }

                    try {
                        // Call the Service layer to perform the deletion
                        inventoryManagementService.deleteClue(clueId);

                        System.out.println("✅ Clue with ID " + clueIdValue + " deleted successfully.");

                    } catch (ClueNotFoundException e) {
                        // Catch explicit exception if the Clue ID doesn't exist
                        System.err.println("❌ Failed to delete clue: " + e.getMessage());
                        System.err.println("⚠️ Clue ID " + clueIdValue + " not found. No action taken.");
                    } catch (RuntimeException e) {
                        // Catch other unexpected runtime errors
                        System.err.println("❌ Failed to delete clue due to an unexpected error: " + e.getMessage());
                    }
                }
                 case 3 -> { // Option for Deleting a Decoration
                    System.out.println("--- 🗑️ Deleting Existing Decoration ---");

                    // 1. Get Decoration ID
                    int decorationIdValue = ConsoleInput.readInt("Enter the ID of the Decoration to Delete: ");
                    Id<Decoration> decorationId = new Id<>(decorationIdValue);

                    // 2. Confirmation
                    String confirmation = ConsoleInput.readLine("⚠️ WARNING: This action is permanent. Confirm deletion of Decoration ID " + decorationIdValue + " (type YES to proceed): ");

                    if (!"YES".equalsIgnoreCase(confirmation.trim())) {
                        System.out.println("❌ Deletion cancelled by user.");
                        break;
                    }

                    try {
                        Boolean success = inventoryManagementService.deleteDecoration(decorationId);

                        if (success != null && success) {
                            System.out.println("✅ Decoration with ID " + decorationIdValue + " deleted successfully.");
                        } else {
                            System.out.println("⚠️ Decoration ID " + decorationIdValue + " not found or failed to delete. No action taken.");
                        }

                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to delete decoration due to an unexpected error: " + e.getMessage());
                    }
                }

                case 0 -> {
                    System.out.println("Returning to Main Menu...");
                    inventoryMenuExit = true;
                }
                default -> {
                    System.out.println("❌ Invalid option. Please choose a number from 0 to 3.");
                }
            }
        }
    }
}
