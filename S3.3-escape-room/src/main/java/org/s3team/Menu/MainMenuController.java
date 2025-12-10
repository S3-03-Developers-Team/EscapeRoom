package org.s3team.Menu;

import java.util.Scanner;

public class MainMenuController {
    private final Scanner SCANNER = new Scanner(System.in);
    private boolean exit = false;

    public void startApplication() {
        while (!exit) {
            try {
                System.out.print("Welcome to Fantastic Escape Rooms");

                System.out.println("\n" +
                        "1- Inventory Manager\n" +
                        "2- Sales Manager\n" +
                        "3- Notification Manager\n" +
                        "0- Exit Application");
                int option = SCANNER.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Changing to Invetory Menu...");
                        InventoryMenu();
                        break;

                    case 2:
                        System.out.println("Changing to Sales Menu...");
                        SalesMenu();
                        break;

                    case 3:
                        System.out.println("Changing to Notification Menu...");
                        NotificationMenu();
                        break;

                    case 0:

                        System.out.println("Exiting");
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }

            } catch (
                    java.util.InputMismatchException e) {
            }

        }
        SCANNER.close();
    }

    private void NotificationMenu() {
    }

    private void SalesMenu() {
    }

    public void InventoryMenu() {
        boolean exitInventory = false;
        while (!exitInventory) {
            try {
                System.out.print("Inventory Manager");

                System.out.println("\n" +
                        "1- Add New Item\n" +
                        "2- Remove Item\n" +
                        "3- Display Inventory\n" +
                        "4- Display Total Value" +
                        "0- Go back\n");
                int option = SCANNER.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Calling service");
                        addItemMenu();
                        break;

                    case 2:
                        System.out.println("Calling service");
                        //removeItem();
                        break;

                    case 3:
                        System.out.println("Calling service");
                        //displayInventory();
                        break;

                    case 4:

                        System.out.println("Calling service");
                        //displayTotalValue();
                        break;

                    case 0:

                        System.out.println("Returning to Main Menu");
                        exitInventory = true;
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }

            } catch (
                    java.util.InputMismatchException e) {

            }


        }
        SCANNER.close();

    }


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

                switch (option) {
                    case 1:
                        // Método para añadir una Sala
                        break;
                    case 2:
                        // Método para añadir una Pista
                        break;
                    case 3:
                        // Método para añadir un Objeto de Decoración
                        break;
                    case 0:
                        addItemMenuExit = true;
                        System.out.println("Returning to Inventory Menu...");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a number from 0 to 3.");
                        break;
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("Input Error: Please enter a valid number.");
                SCANNER.nextLine();
            }
        }
    }
}
