package org.s3team.Menu;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Price;
import org.s3team.ticket.service.TicketService;

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
            System.out.println("6. Players Management");
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
                        appFactory.notificationMenuGenerate().displayNotificationMenu();
                    }

                    case 5 -> {
                        System.out.println("Changing to Certificates Menu...");
                        appFactory.certificateMenuGenerate().showMenu();
                    }
                    case 6 -> {
                        System.out.println("Changing to Player Menu...");
                        appFactory.playerMenuGenerate().displayPlayerMenu();
                    }

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


    private void manageSales() {
        boolean salesMenuExit = false;
        int option;

        while (!salesMenuExit) {

            System.out.println("\n--- SALES MANAGEMENT ---");
            System.out.println("1. Sales Ticket Management");
            System.out.println("2. Display Total Revenue");
            System.out.println("0. Go Back to Main Menu");
            System.out.println("------------------------");

            try {
                option = ConsoleInput.readInt("Choose an option for Sales Management: ");
                switch (option) {
                    case 1 -> appFactory.ticketMenuGenerate().start();
                    case 2 -> displayTotalRevenue(appFactory.ticketService());
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

    private void displayTotalRevenue(TicketService ticketService) {
        Price revenue = ticketService.getTotalRevenue();
        int count = ticketService.countTickets();

        System.out.println("\n================================");
        System.out.println("        📊 TOTAL REVENUE");
        System.out.println("================================");
        System.out.printf(" 🎟️ Tickets sold: %s%n", count);
        System.out.printf(" 💶 Revenue: %s%n", revenue);
        System.out.println("================================\n");
    }
}




