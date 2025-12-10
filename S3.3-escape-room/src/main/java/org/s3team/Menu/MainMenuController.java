package org.s3team.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenuController {


    private final Scanner SCANNER;
    private boolean exit = false;


    public MainMenuController() {
        this.SCANNER = new Scanner(System.in);
    }

    public void startApplication() {
        System.out.println("🚀 Welcome to Fantastic Escape Rooms Manager!");

        while (!exit) {

            // Mostrar menú principal
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Inventory Management");
            System.out.println("2. Sales Management");
            System.out.println("3. Notification Management");
            System.out.println("0. Exit Application");
            System.out.println("-----------------");

            try {
                System.out.print("Choose an option: ");
                int option = SCANNER.nextInt();

                // Limpiamos el buffer después de leer int/double, por si la siguiente lectura es nextLine()
                SCANNER.nextLine();

                switch (option) {
                    case 1:
                        System.out.println("Changing to Inventory Menu...");
                        manageInventory();
                        break;

                    case 2:
                        System.out.println("Changing to Sales Menu...");
                        manageSales();
                        break;

                    case 3:
                        System.out.println("Changing to Notification Menu...");
                        manageNotifications();
                        break;

                    case 0:
                        this.exit = true;
                        System.out.println("👋 Exiting the system. Goodbye!");
                        break;

                    default:
                        System.out.println("❌ Invalid option. Please choose a number from 0 to 3.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
                SCANNER.nextLine(); // Limpiar el buffer si la entrada es incorrecta
            }
        }


        SCANNER.close();
    }

    // Métodos Placeholders
    // Dentro de la clase MainMenuController

    private void manageNotifications() {
        boolean notificationMenuExit = false;
        int option;

        while (!notificationMenuExit) {

            System.out.println("\n--- NOTIFICATION MANAGEMENT ---");
            System.out.println("1. Notify Important Event");
            System.out.println("2. Display Registered Users");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("-------------------------------");

            try {
                System.out.print("Choose an option for Notification Management: ");
                option = SCANNER.nextInt();
                SCANNER.nextLine(); // Limpiar buffer

                switch (option) {
                    case 1:
                        notifyEvent(); // Placeholder para notificar un evento
                        break;
                    case 2:
                        displayRegisteredUsers(); // Placeholder para mostrar usuarios
                        break;
                    case 0:
                        notificationMenuExit = true;
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please choose a number from 0 to 2.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
                SCANNER.nextLine(); // Limpiar el buffer
            }
        }
    }

    // Placeholders para los métodos del sub-menú de Notificaciones
    private void notifyEvent() {
        System.out.println("Notifying Event functionality coming soon...");
    }

    private void displayRegisteredUsers() {
        System.out.println("Displaying Registered Users functionality coming soon...");
    }

    // Dentro de la clase MainMenuController

    private void manageSales() {
        boolean salesMenuExit = false;
        int option;

        while (!salesMenuExit) {

            System.out.println("\n--- SALES MANAGEMENT ---");
            System.out.println("1. Generate Sales Ticket");
            System.out.println("2. Display Total Revenue");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("------------------------");

            try {
                System.out.print("Choose an option for Sales Management: ");
                option = SCANNER.nextInt();
                SCANNER.nextLine(); // Limpiar buffer

                switch (option) {
                    case 1:
                        generateSalesTicket(); // Placeholder para el servicio de venta
                        break;
                    case 2:
                        displayTotalRevenue(); // Placeholder para el servicio de cálculo de ingresos
                        break;
                    case 0:
                        salesMenuExit = true;
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please choose a number from 0 to 2.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
                SCANNER.nextLine(); // Limpiar el buffer
            }
        }
    }

    // Placeholders para los métodos del sub-menú de Ventas
    private void generateSalesTicket() {
        System.out.println("Generating Sales Ticket functionality coming soon...");
    }

    private void displayTotalRevenue() {
        System.out.println("Displaying Total Revenue functionality coming soon...");
    }

    private void removeItem() { System.out.println("\n--- removing element ---");}

    private void displayInventory() { System.out.println("\n--- List of total elements ---");}

    private void displayTotalValue() { System.out.println("\n--- Total value ---"); }

    private void addHint() { System.out.println("\n--- CREATING NEW hint ---"); }

    private void addDecoration() { System.out.println("\n--- CREATING NEW decoration ---");}

    private void addRoom() {

        System.out.println("\n--- CREATING NEW ROOM ---");

    }


    // *** Método Corregido de Inventario ***
    public void manageInventory() {
        boolean inventoryMenuExit = false;
        int option;

        while (!inventoryMenuExit) {

            System.out.println("\n--- INVENTORY MANAGEMENT ---");
            System.out.println("1. Add New Item (Room, Hint, Decoration)");
            System.out.println("2. Remove Item");
            System.out.println("3. Display Inventory");
            System.out.println("4. Display Total Value");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("----------------------------");

            try {
                System.out.print("Choose an option for Inventory Management: ");
                option = SCANNER.nextInt();
                SCANNER.nextLine(); // Limpiar buffer

                switch (option) {
                    case 1:
                        addItemMenu();
                        break;
                    case 2:
                        removeItem();
                        break;
                    case 3:
                        displayInventory();
                        break;
                    case 4:
                        displayTotalValue();
                        break;
                    case 0:
                        inventoryMenuExit = true; // Salir de este bucle
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please choose a number from 0 to 4.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
                SCANNER.nextLine(); // Limpiar el buffer
            }
        }
    }

    // *** Método Corregido de Sub-Menú de Adición ***
    private void addItemMenu() {
        boolean addItemMenuExit = false;
        int option;

        while (!addItemMenuExit) {

            System.out.println("\n--- ADD NEW ITEM ---");
            System.out.println("1. Add New Room");
            System.out.println("2. Add Hint");
            System.out.println("3. Add Decoration Object");
            System.out.println("0. Go Back to Inventory Menu");
            System.out.println("--------------------");

            try {
                System.out.print("Choose item type to add: ");
                option = SCANNER.nextInt();
                SCANNER.nextLine(); // Limpiar buffer

                switch (option) {
                    case 1:
                        addRoom(); // Aquí llamamos al método a implementar
                        break;
                    case 2:
                        addHint();
                        break;
                    case 3:
                        addDecoration();
                        break;
                    case 0:
                        addItemMenuExit = true;
                        System.out.println("Returning to Inventory Menu...");
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please choose a number from 0 to 3.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
                SCANNER.nextLine(); // Limpiar el buffer
            }
        }
    }

}