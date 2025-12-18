package org.s3team.inventoryMenu;

import org.s3team.common.util.ConsoleInput;
import org.s3team.inventoryService.InventoryQueryService;

/**
 * ClassName: DisplayInventoryQuantityMenu
 * Package: org.s3team.inventoryMenu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 22:09
 * Version:v1.0
 *
 */
public class DisplayInventoryQuantityMenu {
    private final InventoryQueryService inventoryQueryService;

    public DisplayInventoryQuantityMenu(InventoryQueryService inventoryQueryService) {
        this.inventoryQueryService = inventoryQueryService;
    }

    public void displayInventoryQuantity() {
        boolean inventoryMenuExit = false;

        while (!inventoryMenuExit) {

            System.out.println("\n--- Display Items Quantities ---");
            System.out.println("1. Display Room Quantity");
            System.out.println("2. Display Clue Quantity");
            System.out.println("3. Display Decoration Quantity");
            System.out.println("4. Display All Items Quantity");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            int option = ConsoleInput.readInt("Choose an option (0-4) : ");

            switch (option) {
            case 1 -> { // Option for Displaying Room Quantity
                System.out.println("--- 📊 Displaying Room Count ---");

                try {
                    // Call the Service layer to get the total count
                    int roomCount = inventoryQueryService.countRooms();

                    // Display the result
                    System.out.println("\n✅ Total Room Quantity Found:");
                    System.out.println("---------------------------------");
                    System.out.println("Total Rooms in Inventory: " + roomCount);
                    System.out.println("---------------------------------");

                } catch (RuntimeException e) {
                    // Handle any unexpected errors (e.g., database connection issue)
                    System.err.println("❌ An unexpected error occurred while counting rooms: " + e.getMessage());
                }
            }
                case 2 -> { // Option for Displaying Clue Quantity
                    System.out.println("--- 📊 Displaying Clue Count ---");

                    try {
                        int clueCount = inventoryQueryService.countClues();

                        System.out.println("\n✅ Total Clue Quantity Found:");
                        System.out.println("---------------------------------");
                        System.out.println("Total Clues in Inventory: " + clueCount);
                        System.out.println("---------------------------------");

                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while counting clues: " + e.getMessage());
                    }
                }
                case 3 -> { // Option for Displaying Decoration Quantity
                    System.out.println("--- 📊 Displaying Decoration Count ---");

                    try {
                        int decorationCount = inventoryQueryService.countDecorations();

                        System.out.println("\n✅ Total Decoration Quantity Found:");
                        System.out.println("---------------------------------");
                        System.out.println("Total Decorations in Inventory: " + decorationCount);
                        System.out.println("---------------------------------");

                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while counting decorations: " + e.getMessage());
                    }
                }
                case 4 -> { // Option for Displaying All Items Quantity
                    System.out.println("--- 📊 Displaying All Inventory Counts ---");

                    try {
                        int roomCount = inventoryQueryService.countRooms();
                        int clueCount = inventoryQueryService.countClues();
                        int decorationCount = inventoryQueryService.countDecorations();

                        int totalItems = roomCount + clueCount + decorationCount;

                        System.out.println("\n✅ Summary of All Inventory Items:");
                        System.out.println("---------------------------------");
                        System.out.printf("Rooms:       %d%n", roomCount);
                        System.out.printf("Clues:       %d%n", clueCount);
                        System.out.printf("Decorations: %d%n", decorationCount);
                        System.out.println("---------------------------------");
                        System.out.printf("TOTAL ITEMS: %d%n", totalItems);
                        System.out.println("---------------------------------");

                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while counting all items: " + e.getMessage());
                    }
                }
                case 0 -> { // Go Back
                    System.out.println("Returning to Main Menu...");
                    inventoryMenuExit = true;
                }
                default -> {
                    System.out.println("❌ Invalid option. Please choose a number from 0 to 4.");
                }
            }
        }
    }
}