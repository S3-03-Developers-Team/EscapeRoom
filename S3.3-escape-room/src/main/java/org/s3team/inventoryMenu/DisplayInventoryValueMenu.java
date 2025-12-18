package org.s3team.inventoryMenu;

import org.s3team.common.util.ConsoleInput;
import org.s3team.inventoryService.InventoryQueryService;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ClassName: DisplayInventoryValueMenu
 * Package: org.s3team.inventoryMenu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 22:08
 * Version:v1.0
 *
 */
public class DisplayInventoryValueMenu {
    private final InventoryQueryService inventoryQueryService;

    public DisplayInventoryValueMenu(InventoryQueryService inventoryQueryService) {
        this.inventoryQueryService = inventoryQueryService;
    }

    public void displayInventoryValue() {
        boolean inventoryMenuExit = false;

        while (!inventoryMenuExit) {

            System.out.println("\n--- Display Items Values ---");
            System.out.println("1. Display Room Value");
            System.out.println("2. Display Clue Value");
            System.out.println("3. Display Decoration Value");
            System.out.println("4. Display All Items Value");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            int option = ConsoleInput.readInt("Choose an option (0-4) : ");

            switch (option) {

                case 1 -> { // Option for Displaying Room Value
                    System.out.println("--- 💰 Displaying Room Total Value ---");

                    try {
                        BigDecimal roomValue = inventoryQueryService.calculateRoomTotalPrice().value();

                        System.out.println("\n✅ Total Room Value Found:");
                        System.out.println("---------------------------------");
                        System.out.printf("Total Value of All Rooms: $%.2f%n", roomValue);
                        System.out.println("---------------------------------");

                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while calculating room value: " + e.getMessage());
                    }
                }
                case 2 -> { // Option for Displaying Clue Value
                    System.out.println("--- 💰 Displaying Clue Total Value ---");

                    try {
                        BigDecimal clueValue = inventoryQueryService.calculateClueTotalPrice().value();

                        System.out.println("\n✅ Total Clue Value Found:");
                        System.out.println("---------------------------------");
                        System.out.printf("Total Value of All Clues: $%.2f%n", clueValue);
                        System.out.println("---------------------------------");

                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while calculating clue value: " + e.getMessage());
                    }
                }
                case 3 -> { // Option for Displaying Decoration Value
                    System.out.println("--- 💰 Displaying Decoration Total Value ---");

                    try {
                        BigDecimal decorationValue = inventoryQueryService.calculateDecorationTotalPrice().value();

                        System.out.println("\n✅ Total Decoration Value Found:");
                        System.out.println("---------------------------------");
                        System.out.printf("Total Value of All Decorations: $%.2f%n", decorationValue);
                        System.out.println("---------------------------------");

                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while calculating decoration value: " + e.getMessage());
                    }
                }
                case 4 -> { // Option for Displaying All Items Value
                    System.out.println("--- 💰 Displaying All Inventory Total Value ---");

                    try {
                        // Retrieve individual values
                        BigDecimal roomValue = inventoryQueryService.calculateRoomTotalPrice().value();
                        BigDecimal clueValue = inventoryQueryService.calculateClueTotalPrice().value();
                        BigDecimal decorationValue = inventoryQueryService.calculateDecorationTotalPrice().value();

                        // Calculate the grand total
                        BigDecimal totalValue = roomValue.add(clueValue).add(decorationValue);

                        // Round the final value for display (using HALF_UP for standard rounding)
                        BigDecimal roundedTotal = totalValue.setScale(2, RoundingMode.HALF_UP);

                        // Display the composite result
                        System.out.println("\n✅ Summary of All Inventory Values:");
                        System.out.println("---------------------------------");
                        System.out.printf("Rooms Total Value:       $%.2f%n", roomValue);
                        System.out.printf("Clues Total Value:       $%.2f%n", clueValue);
                        System.out.printf("Decorations Total Value: $%.2f%n", decorationValue);
                        System.out.println("---------------------------------");
                        System.out.printf("GRAND TOTAL VALUE:       $%.2f%n", roundedTotal);
                        System.out.println("---------------------------------");

                    } catch (RuntimeException e) {
                        System.err.println("❌ An unexpected error occurred while calculating all inventory values: " + e.getMessage());
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