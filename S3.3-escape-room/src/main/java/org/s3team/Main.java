package org.s3team;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.Menu.MainMenuController;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- S3.3 Escape Room Application Starting ---");

        // 1. Intentar obtener la instancia Singleton y abrir la conexión
        try {
            // El método getInstance() intentará conectar a MySQL.
            // Si falla, lanzará una RuntimeException y el programa terminará.
            MySQL_Data_Base_Connection dbInstance = MySQL_Data_Base_Connection.getInstance();

            // 2. Si llegamos aquí, la conexión fue exitosa.
            Connection conn = dbInstance.getConnection();

            System.out.println("Database Connection Status: SUCCESS!");
            System.out.println("JDBC Connection Object: " + conn);

            // Aquí iría el resto de la lógica de tu aplicación


        } catch (RuntimeException e) {
            System.err.println("FATAL ERROR: Application failed to initialize due to connection failure.");
            System.err.println("Check DB container status, network, and credentials.");
            e.printStackTrace();
            // Salida con código de error para que Docker sepa que falló el inicio
            System.exit(1);
        }

        MainMenuController startApp = new MainMenuController();
        startApp.startApplication();
    }
}