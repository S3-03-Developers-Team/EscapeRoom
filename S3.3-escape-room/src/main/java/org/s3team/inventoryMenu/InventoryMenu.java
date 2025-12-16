package org.s3team.inventoryMenu;

import org.s3team.common.util.ConsoleInput;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * ClassName: InventoryMenu
 * Package: org.s3team.inventoryMenu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 21:38
 * Version:v1.0
 *
 */
public class InventoryMenu {
    private final AddItemMenu addItemMenu;
    private final UpdateItemMenu updateItemMenu;
    private final RemoveItemMenu removeItemMenu;
    private final DisplayInventoryMenu displayInventoryMenu;
    private final DisplayInventoryValueMenu displayInventoryValueMenu;
    private final DisplayInventoryQuantityMenu displayInventoryQuantityMenu;

    public InventoryMenu(AddItemMenu addItemMenu, UpdateItemMenu updateItemMenu, RemoveItemMenu removeItemMenu, DisplayInventoryMenu displayInventoryMenu, DisplayInventoryValueMenu displayInventoryValueMenu, DisplayInventoryQuantityMenu displayInventoryQuantityMenu) {
        this.addItemMenu = addItemMenu;
        this.removeItemMenu = removeItemMenu;
        this.updateItemMenu = updateItemMenu;
        this.displayInventoryMenu = displayInventoryMenu;
        this.displayInventoryValueMenu = displayInventoryValueMenu;
        this.displayInventoryQuantityMenu = displayInventoryQuantityMenu;
    }


    public void inventoryMenuStart() {

        boolean inventoryMenuExit = false;
        int option;

        while (!inventoryMenuExit) {

            System.out.println("\n--- INVENTORY MANAGEMENT ---");
            System.out.println("1. Add New Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item");
            System.out.println("4. Display Inventory");
            System.out.println("5. Display Inventory Total Value");
            System.out.println("6. Display Inventory Total Quantity");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            try {
                System.out.print("Choose an option for Inventory Management: ");
                option = ConsoleInput.readInt( "Please enter the number between 0-6.");

                switch (option) {
                    case 1 -> addItemMenu.addItem();
                    case 2 -> removeItemMenu.removeItem();
                    case 3 -> updateItemMenu.updateItemMenu();
                    case 4 -> displayInventoryMenu.displayInventory();
                    case 5 -> displayInventoryValueMenu.displayInventoryValue();
                    case 6 -> displayInventoryQuantityMenu.displayInventoryQuantity();
                    case 0 ->{
                        System.out.println("Returning to Main Menu...");
                    inventoryMenuExit = true;
                    }
                    default -> System.out.println("❌ Invalid option. Please choose a number from 0 to 6.");

                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
            }
        }
    }

}
