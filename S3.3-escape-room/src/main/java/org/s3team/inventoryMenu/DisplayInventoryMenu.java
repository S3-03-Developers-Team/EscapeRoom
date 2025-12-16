package org.s3team.inventoryMenu;

import org.s3team.Exceptions.ClueNotFoundException;
import org.s3team.Exceptions.DecorationNotFoundException;
import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.clue.model.Clue;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;
import org.s3team.decoration.model.Decoration;
import org.s3team.inventoryService.InventoryQueryService;
import org.s3team.room.model.Room;

/**
 * ClassName: DisplayInvetoryMenu
 * Package: org.s3team.inventoryMenu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 21:59
 * Version:v1.0
 *
 */
public class DisplayInventoryMenu {
    private final InventoryQueryService inventoryQueryService;

    public DisplayInventoryMenu(InventoryQueryService inventoryQueryService) {
        this.inventoryQueryService = inventoryQueryService;
    }

    public void displayInventory() {
        boolean inventoryMenuExit = false;

        while (!inventoryMenuExit) {

            System.out.println("\n--- Display Items ---");
            System.out.println("1. Find Room By Id");
            System.out.println("2. Find Clue By Id");
            System.out.println("3. Find Decoration By Id");
            System.out.println("4. List All Rooms");
            System.out.println("5. List All Clues");
            System.out.println("6. List All Decorations");
            System.out.println("7. List All Items");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            int option = ConsoleInput.readInt("Choose an option (0-7) : ");

            switch (option) {
                case 1 -> { // Option for Finding a Room by ID
                    System.out.println("--- 🔎 Find Room by ID ---");

                    // 1. Get Room ID from user input
                    int roomIdValue = ConsoleInput.readInt("Enter the ID of the Room to search: ");
                    Id<Room> roomId = new Id<>(roomIdValue);

                    try {
                        // 2. Call the Service layer to retrieve the Room
                        Room room = inventoryQueryService.findRoomById(roomId);

                        // 3. Display the details of the found Room
                        System.out.println("\n✅ Room Found:");
                        System.out.println("---------------------------------");
                        System.out.println("ID: " + room.getRoomId().value());
                        System.out.println("Name: " + room.getName().value());
                        System.out.println("Difficulty: " + room.getDifficulty());
                        System.out.println("Price: " + room.getPrice().value());
                        System.out.println("Theme ID: " + room.getThemeId().value());
                        System.out.println("---------------------------------");

                    } catch (RoomNotFoundException e) {
                        // Handle the case where the ID does not exist
                        System.err.println("❌ Search Failed: " + e.getMessage());
                        System.err.println("⚠️ Room with ID " + roomIdValue + " not found in inventory.");
                    } catch (RuntimeException e) {
                        // Handle other unexpected errors (e.g., database connection issue)
                        System.err.println("❌ An unexpected error occurred while searching: " + e.getMessage());
                    }
                }
                case 2 ->{ // Option for Finding a Clue by ID
                    System.out.println("--- 🔎 Find Clue by ID ---");

                    int clueIdValue = ConsoleInput.readInt("Enter the ID of the Clue to search: ");
                    Id<Clue> clueId = new Id<>(clueIdValue);

                    try {
                        Clue clue = inventoryQueryService.getClueById(clueId);

                        System.out.println("\n✅ Clue Found:");
                        System.out.println("---------------------------------");
                        System.out.println("ID: " + clue.getId().value());
                        System.out.println("Type: " + clue.getType());
                        System.out.println("Description: " + clue.getDescription().value());
                        System.out.println("Price: " + clue.getPrice().value());
                        System.out.println("Theme ID: " + clue.getThemeId().value());
                        System.out.println("Room ID: " + clue.getRoomId().value());
                        System.out.println("---------------------------------");

                    } catch (ClueNotFoundException e) {
                        System.err.println("❌ Search Failed: " + e.getMessage());
                        System.err.println("⚠️ Clue with ID " + clueIdValue + " not found in inventory.");
                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while searching: " + e.getMessage());
                    }
                }
                case 3 ->{ // Option for Finding a Decoration by ID
                    System.out.println("--- 🔎 Find Decoration by ID ---");

                    int decorationIdValue = ConsoleInput.readInt("Enter the ID of the Decoration to search: ");
                    Id<Decoration> decorationId = new Id<>(decorationIdValue);

                    try {
                        Decoration decoration = inventoryQueryService.findDecorationById(decorationId);

                        System.out.println("\n✅ Decoration Found:");
                        System.out.println("---------------------------------");
                        // 假设 Decoration 模型中有 getId() 和 getRoomId()
                        System.out.println("ID: " + decoration.getIdDecorationObject());
                        System.out.println("Name: " + decoration.getName());
                        System.out.println("Material: " + decoration.getMaterial());
                        System.out.println("Stock: " + decoration.getStock());
                        System.out.println("Price: " + decoration.getPrice()); // 假设 price 是 BigDecimal
                        System.out.println("Room ID: " + decoration.getRoomId()); // 假设 RoomId 是 int 或 Id<Room>

                        System.out.println("---------------------------------");

                    } catch (DecorationNotFoundException e) {
                        System.err.println("❌ Search Failed: " + e.getMessage());
                        System.err.println("⚠️ Decoration with ID " + decorationIdValue + " not found in inventory.");
                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while searching: " + e.getMessage());
                    }
                }
                case 4 -> { // List All Rooms
                    System.out.println("--- 📄 Listing All Rooms ---");
                    try {
                        inventoryQueryService.listRooms().forEach(room -> {
                            System.out.printf("Room ID: %d, Name: %s, Difficulty: %s%n",
                                    room.getRoomId().value(),
                                    room.getName().value(),
                                    room.getDifficulty());
                        });
                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to list rooms: " + e.getMessage());
                    }
                }
                case 5 -> { // List All Clues
                    System.out.println("--- 📄 Listing All Clues ---");
                    try {
                        inventoryQueryService.listClues().forEach(clue -> {
                            System.out.printf("Clue ID: %d, Type: %s, Room ID: %d%n",
                                    clue.getId().value(),
                                    clue.getType(),
                                    clue.getRoomId().value());
                        });
                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to list clues: " + e.getMessage());
                    }
                }
                case 6 -> { // List All Decorations
                    System.out.println("--- 📄 Listing All Decorations ---");
                    try {
                        inventoryQueryService.listDecorations().forEach(decoration -> {
                            System.out.printf("Decoration ID: %d, Name: %s, Material: %s, Stock: %d%n",
                                    decoration.getIdDecorationObject(),
                                    decoration.getName(),
                                    decoration.getMaterial(),
                                    decoration.getStock());
                        });
                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to list decorations: " + e.getMessage());
                    }
                }
                case 7 -> { // List All Items (Composite View)
                    System.out.println("--- 📄 Listing ALL Inventory Items ---");
                    System.out.println("--- Rooms ---");
                    try {
                        inventoryQueryService.listRooms().forEach(room -> {
                            System.out.printf("[R] ID: %d, Name: %s, Price: %.2f%n",
                                    room.getRoomId().value(),
                                    room.getName().value(),
                                    room.getPrice().value());
                        });
                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to list rooms in composite view: " + e.getMessage());
                    }
                    System.out.println("--- Clues ---");
                    try {
                        inventoryQueryService.listClues().forEach(clue -> {
                            System.out.printf("[C] ID: %d, Type: %s, Price: %.2f%n",
                                    clue.getId().value(),
                                    clue.getType(),
                                    clue.getPrice().value());
                        });
                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to list clues in composite view: " + e.getMessage());
                    }
                    System.out.println("--- Decorations ---");
                    try {
                        inventoryQueryService.listDecorations().forEach(decoration -> {
                            System.out.printf("[D] ID: %d, Name: %s, Stock: %d%n",
                                    decoration.getIdDecorationObject(),
                                    decoration.getName(),
                                    decoration.getStock());
                        });
                    } catch (RuntimeException e) {
                        System.err.println("❌ Failed to list decorations in composite view: " + e.getMessage());
                    }
                }

                case 0 -> {
                    System.out.println("Returning to Main Menu...");
                    inventoryMenuExit = true;
                }
                default -> {
                    System.out.println("❌ Invalid option. Please choose a number from 0 to 7.");
                }
            }
        }
    }
}

