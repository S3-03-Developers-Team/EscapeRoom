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
 * ClassName: AddItemMenu
 * Package: org.s3team.inventoryMenu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 21:38
 * Version:v1.0
 *
 */
public class AddItemMenu {

    private final InventoryManagementService inventoryManagementService;

    public AddItemMenu(InventoryManagementService inventoryManagementService) {
        this.inventoryManagementService = inventoryManagementService;
    }

    public void addItem() {
        boolean inventoryMenuExit = false;

        while (!inventoryMenuExit) {

            System.out.println("\n--- Add Items ---");
            System.out.println("1. Add New Room");
            System.out.println("2. Add New Clue");
            System.out.println("3. Add New Decoration");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            int option = ConsoleInput.readInt("Choose an option (0-3) : ");

            switch (option) {
                case 1 -> {
                    System.out.println("--- 🚪 Adding New Room ---");

                    String nameInput = ConsoleInput.readLine("Enter Room Name: ");
                    Name name = new Name(nameInput);

                    Difficulty difficulty = null;
                    while (difficulty == null) {
                        String difficultyInput = ConsoleInput.readLine("Enter Difficulty (EASY, MEDIUM, HARD): ").toUpperCase();
                        try {
                            difficulty = Difficulty.valueOf(difficultyInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Invalid difficulty level. Please use EASY, MEDIUM, or HARD.");
                        }
                    }

                    BigDecimal priceAmount = ConsoleInput.readBigDecimal("Enter Price (e.g., 99.99): ");
                    Price price = new Price(priceAmount);

                    int themeIdValue = ConsoleInput.readInt("Enter Theme ID: ");
                    Id<Theme> themeId = new Id<>(themeIdValue);

                    try {
                        Room room = Room.createNew(name, difficulty, price, themeId);
                        Room savedRoom = inventoryManagementService.addRoom(room);
                        System.out.println("✅ Room added successfully!");
                        System.out.println(savedRoom);

                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to add room: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("--- 🔑 Adding New Clue ---");

                    ClueType type = null;
                    while (type == null) {
                        String typeInput = ConsoleInput.readLine("Enter Clue Type (TEXT, OBJECT, SOUND): ").toUpperCase();
                        try {
                            type = ClueType.valueOf(typeInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Invalid clue type. Please use TEXT, OBJECT, or SOUND.");
                        }
                    }

                    String descriptionInput = ConsoleInput.readLine("Enter Clue Description: ");
                    ClueDescription description = new ClueDescription(descriptionInput);

                    BigDecimal priceAmount = ConsoleInput.readBigDecimal("Enter Price (e.g., 5.00): ");
                    Price price = new Price(priceAmount);

                    int themeIdValue = ConsoleInput.readInt("Enter Theme ID for the clue: ");
                    Id<Theme> themeId = new Id<>(themeIdValue);

                    int roomIdValue = ConsoleInput.readInt("Enter Room ID the clue belongs to: ");
                    Id<Room> roomId = new Id<>(roomIdValue);

                    try {
                        Clue savedClue = inventoryManagementService.addClue(
                                type,
                                description,
                                price,
                                themeId,
                                roomId
                        );

                        System.out.println("✅ Clue added successfully!");
                        System.out.println(savedClue);

                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to add clue: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("--- 🖼️ Adding New Decoration Object ---");

                    String nameInput = ConsoleInput.readLine("Enter Decoration Name: ");

                    Material material = null;
                    while (material == null) {
                        String materialInput = ConsoleInput.readLine("Enter Material (WOOD, METAL, PLASTIC): ").toUpperCase();
                        try {
                            material = Material.valueOf(materialInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Invalid material. Please use WOOD, METAL, or PLASTIC.");
                        }
                    }

                    int stockValue = ConsoleInput.readInt("Enter Stock Quantity: ");

                    BigDecimal priceAmount = ConsoleInput.readBigDecimal("Enter Price (e.g., 10.50): ");

                    int roomIdValue = ConsoleInput.readInt("Enter Target Room ID: ");

                    try {

                        Decoration newDecoration = new Decoration(
                                nameInput,
                                material,
                                stockValue,
                                priceAmount,
                                roomIdValue
                        );

                        inventoryManagementService.addDecoration(newDecoration);

                        System.out.println("✅ Decoration item creation request processed.");

                    } catch (IllegalArgumentException e) {
                        System.err.println("❌ Failed to add decoration: " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred during saving: " + e.getMessage());
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

