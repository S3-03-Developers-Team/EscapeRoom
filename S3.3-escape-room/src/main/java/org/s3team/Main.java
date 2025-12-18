package org.s3team;

import org.s3team.dataBaseConnection.MySQLDataBaseConnection;
import org.s3team.menu.MainMenuController;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- S3.3 Escape Room Application Starting ---");

        try {
            MySQLDataBaseConnection dbInstance = MySQLDataBaseConnection.getInstance();

            Connection conn = dbInstance.getConnection();

            System.out.println("Database Connection Status: SUCCESS!");
            System.out.println("JDBC Connection Object: " + conn);

        } catch (RuntimeException e) {
            System.err.println("FATAL ERROR: Application failed to initialize due to connection failure.");
            System.err.println("Check DB container status, network, and credentials.");
            e.printStackTrace();
            System.exit(1);
        }

        MainMenuController startApp = new MainMenuController();
        startApp.startApplication();
    }
}