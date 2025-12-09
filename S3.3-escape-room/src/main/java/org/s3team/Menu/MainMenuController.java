package org.s3team.Menu;

import java.util.Scanner;

public class MainMenuController {
    private final Scanner SCANNER = new Scanner(System.in);
    private boolean exit = false;

    public void startApplication() {
        while (!exit) {
            try {
                System.out.print("Welcome to Fantastic Escape Rooms");

                System.out.println("Opción,Funcionalidad\n" +
                        "1,\"Gestión de Inventario (Salas, Pistas, Objetos)\"\n" +
                        "2,Gestión de Ventas (Tickets e Ingresos)\n" +
                        "3,Gestión de Notificaciones y Usuarios\n" +
                        "0,Salir de la aplicación");
                int option = SCANNER.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Changing to Invetory Menu");
                        break;

                    case 2:
                        System.out.println("Changing to Sales Menu");
                        break;

                    case 3:
                        System.out.println("Changing to Notification Menu");
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


}
