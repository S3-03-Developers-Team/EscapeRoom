package org.s3team.Menu;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.common.util.ConsoleInput;

import java.util.InputMismatchException;

public class MainMenuController {

    private final AppFactory appFactory;
    private boolean exit = false;


    public MainMenuController() {
        this.appFactory = new AppFactory(MySQL_Data_Base_Connection.getInstance());
    }

    public void startApplication() {
        System.out.println("🚀 Welcome to Fantastic Escape Rooms Manager!");

        while (!exit) {

            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Theme Management");
            System.out.println("2. Inventory Management");
            System.out.println("3. Sales Management");
            System.out.println("4. Notification Management");
            System.out.println("5. Certificates Management");
            System.out.println("0. Exit Application");
            System.out.println("-----------------");

            try {
                int option = ConsoleInput.readInt("Choose an option:");

                switch (option) {
                    case 1 -> {
                        System.out.println("Changing to Theme Menu...");
                        appFactory.themeMenuGenerate().themeMenu();

                    }

                    case 2 -> {
                        System.out.println("Changing to Inventory Menu...");
                        appFactory.inventoryMenuGenerate().inventoryMenuStart();
                    }

                    case 3 -> {
                        System.out.println("Changing to Sales Menu...");
                        manageSales();
                    }

                    case 4 -> {
                        System.out.println("Changing to Notification Menu...");
                        manageNotifications();
                    }

               /*     case 5 -> {
                        System.out.println("Changing to Certificates Menu...");
                        appFactory.certificateMenuGenerate().showMenu();
                    }*/

                    case 0 -> {
                        this.exit = true;
                        System.out.println("👋 Exiting the system. Goodbye!");
                    }

                    default -> System.out.println("❌ Invalid option. Please choose a number from 0 to 3.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
            }
        }
    }


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
                option = ConsoleInput.readInt("Choose an option for Notification Management:");

                switch (option) {
                    case 1 -> notifyEvent();
                    case 2 -> displayRegisteredUsers();
                    case 0 -> {
                        notificationMenuExit = true;
                        System.out.println("Returning to Main Menu...");
                    }
                    default -> System.out.println("❌ Invalid option. Please choose a number from 0 to 2.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
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
                option = ConsoleInput.readInt("Choose an option for Sales Management: ");
                switch (option) {
                    case 1 -> generateSalesTicket();
                    case 2 -> displayTotalRevenue();
                    case 0 -> {
                        salesMenuExit = true;
                        System.out.println("Returning to Main Menu...");
                    }
                    default -> System.out.println("❌ Invalid option. Please choose a number from 0 to 2.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⛔ Input Error: Please enter a valid number.");
            }
        }
    }

    private void generateSalesTicket() {
        System.out.println("Generating Sales Ticket functionality coming soon...");
    }

    private void displayTotalRevenue() {
        System.out.println("Displaying Total Revenue functionality coming soon...");
    }
}




