package org.s3team.dataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class MySQLDataBaseConnection implements DataBaseConnection {

    private static MySQLDataBaseConnection instance;
    private String url;
    private String user;
    private String password;

    private MySQLDataBaseConnection() {
        getDatabaseProperties();

    }

    public static synchronized MySQLDataBaseConnection getInstance() {
        if (instance == null) {
            try {
                instance = new MySQLDataBaseConnection();

            } catch (Exception e) {
                throw new RuntimeException("Error connecting to the database", e);
            }
        }
        return instance;
    }

    private void getDatabaseProperties() {
        Map<String, String> env = System.getenv();
        url = env.get("DB_URL");
        user = env.get("DB_USER");
        password = env.get("DB_PASSWORD");
    }

    @Override
    public void openConnection() {
//        try {
//            getInstance();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error connecting to the database", e);
//        }

    }

    public Connection getConnection() {

        try {

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {

            throw new RuntimeException("Error obtaining new database connection", e);
        }
    }

    @Override
    public void closeConnection() {
//        if (this.getConnection() != null) {
//            try {
//                instance.getConnection().close();
//            } catch (SQLException e) {
//                throw new RuntimeException("Error closing connection to data base", e);
//            }
//        }
    }
}