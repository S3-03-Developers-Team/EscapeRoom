package org.s3team.inventoryMenu;

import org.s3team.clue.model.Clue;
import org.s3team.clue.model.ClueDescription;
import org.s3team.clue.model.ClueType;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.decoration.model.Decoration;
import org.s3team.decoration.model.Material;
import org.s3team.inventoryService.InventoryManagementService;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;
import org.s3team.theme.model.Theme;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * ClassName: UpdateItemMenu
 * Package: org.s3team.inventoryMenu
 * Description:
 * Author: Rong Jiang
 * Create:13/12/2025 - 19:01
 * Version:v1.0
 *
 */
public class UpdateItemMenu {
    private final InventoryManagementService inventoryManagementService;

    public UpdateItemMenu(InventoryManagementService inventoryManagementService) {
        this.inventoryManagementService = inventoryManagementService;
    }

    public void updateItemMenu() {
        boolean inventoryMenuExit = false;

        while (!inventoryMenuExit) {

            System.out.println("\n--- 🔄 Update Items ---");
            System.out.println("1. Update Room");
            System.out.println("2. Update Clue");
            System.out.println("3. Update Decoration");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            // 使用 ConsoleInput 读取选项
            int option = ConsoleInput.readInt("Choose an option (0-3): ");

            switch (option) {
                case 1 -> { // Option for Updating a Room
                    System.out.println("--- 🔄 Updating Existing Room ---");

                    // 1. Get Room ID
                    int roomIdValue = ConsoleInput.readInt("Enter the ID of the Room to Update: ");
                    Id<Room> roomId = new Id<>(roomIdValue);

                    // 2. Get Name
                    String nameInput = ConsoleInput.readLine("Enter the NEW Room Name: ");
                    Name name = new Name(nameInput);

                    // 3. Get Difficulty
                    Difficulty difficulty = null;
                    while (difficulty == null) {
                        String difficultyInput = ConsoleInput.readLine("Enter the NEW Difficulty (EASY, MEDIUM, HARD): ").toUpperCase();
                        try {
                            difficulty = Difficulty.valueOf(difficultyInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Invalid difficulty level. Please use EASY, MEDIUM, or HARD.");
                        }
                    }

                    // 4. Get Price
                    BigDecimal priceAmount = ConsoleInput.readBigDecimal("Enter the NEW Price (e.g., 99.99): ");
                    Price price = new Price(priceAmount);

                    // 5. Get Theme ID
                    int themeIdValue = ConsoleInput.readInt("Enter the NEW Theme ID: ");
                    Id<Theme> themeId = new Id<>(themeIdValue);

                    try {
                        Room roomToUpdate = Room.rehydrate(roomId, name, difficulty, price, themeId);
                        boolean success = inventoryManagementService.updateRoom(roomToUpdate);

                        if (success) {
                            System.out.println("✅ Room with ID " + roomIdValue + " updated successfully.");
                        } else {
                            System.out.println("⚠️ Update for Room ID " + roomIdValue + " completed, but no changes were applied (data may be identical).");
                        }

                    } catch (RuntimeException e) {
                        System.err.println("❌ Error updating the room: " + e.getMessage());
                    }
                }

                case 2 -> { // Option for Updating a Clue
                    System.out.println("--- 🔄 Updating Existing Clue ---");

                    // 1. Get Clue ID
                    int clueIdValue = ConsoleInput.readInt("Enter the ID of the Clue to Update: ");
                    Id<Clue> clueId = new Id<>(clueIdValue);

                    // 2. Get Clue Type (New Value)
                    ClueType type = null;
                    while (type == null) {
                        String typeInput = ConsoleInput.readLine("Enter NEW Clue Type (TEXT, OBJECT, SOUND): ").toUpperCase();
                        try {
                            type = ClueType.valueOf(typeInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Invalid clue type. Please use TEXT, OBJECT, or SOUND.");
                        }
                    }

                    // 3. Get Description (New Value)
                    String descriptionInput = ConsoleInput.readLine("Enter NEW Clue Description: ");
                    ClueDescription description = new ClueDescription(descriptionInput);

                    // 4. Get Price (New Value)
                    BigDecimal priceAmount = ConsoleInput.readBigDecimal("Enter NEW Price (e.g., 5.00): ");
                    Price price = new Price(priceAmount);

                    // 5. Get Theme ID (New Value)
                    int themeIdValue = ConsoleInput.readInt("Enter NEW Theme ID for the clue: ");
                    Id<Theme> themeId = new Id<>(themeIdValue);

                    // 6. Get Room ID (New Value)
                    int roomIdValue = ConsoleInput.readInt("Enter NEW Room ID the clue belongs to: ");
                    Id<Room> roomId = new Id<>(roomIdValue);

                    try {
                        Clue clueToUpdate = Clue.rehydrate(clueId, type, description, price, themeId, roomId);
                        boolean success = inventoryManagementService.updateClue(clueToUpdate);

                        if (success) {
                            System.out.println("✅ Clue with ID " + clueIdValue + " updated successfully.");
                        } else {
                            System.out.println("⚠️ Update for Clue ID " + clueIdValue + " completed, but no changes were applied (data may be identical).");
                        }

                    } catch (RuntimeException e) {
                        System.err.println("❌ Error updating the clue: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("--- 🔄 Updating Existing Decoration Object ---");

                    // 1. Get Decoration ID
                    int decorationIdValue = ConsoleInput.readInt("Enter the ID of the Decoration to Update: ");

                    // 2. Get Name (New Value)
                    String nameInput = ConsoleInput.readLine("Enter the NEW Decoration Name: ");

                    // 3. Get Material (New Value)
                    Material material = null;
                    while (material == null) {
                        String materialInput = ConsoleInput.readLine("Enter NEW Material (WOOD, METAL, PLASTIC): ").toUpperCase();
                        try {
                            material = Material.valueOf(materialInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Invalid material. Please use WOOD, METAL, or PLASTIC.");
                        }
                    }

                    // 4. Get Stock (New Value)
                    int stockValue = ConsoleInput.readInt("Enter NEW Stock Quantity: ");

                    // 5. Get Price (New Value)
                    BigDecimal priceAmount = ConsoleInput.readBigDecimal("Enter NEW Price (e.g., 10.50): ");

                    // 6. Get Room ID (New Value)
                    int roomIdValue = ConsoleInput.readInt("Enter NEW Target Room ID: ");

                    try {
                        Decoration decorationToUpdate = new Decoration(
                                decorationIdValue,
                                nameInput,
                                material,
                                stockValue,
                                priceAmount,
                                roomIdValue
                        );

                        boolean success = inventoryManagementService.updateDecoration(decorationToUpdate);

                        if (success) {
                            System.out.println("✅ Decoration with ID " + decorationIdValue + " updated successfully.");
                        } else {
                            System.out.println("⚠️ Update for Decoration ID " + decorationIdValue + " completed, but no changes were applied (data may be identical).");
                        }

                    } catch (IllegalArgumentException e) {
                        System.err.println("❌ Failed to update decoration: " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("❌ Error updating the decoration: " + e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Returning to Main Menu...");
                    inventoryMenuExit = true;
                }
                default -> System.out.println("❌ Invalid option. Please choose a number from 0 to 3.");
            }
        }
    }
}

