package org.s3team.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class MySQL_Data_Base_Connection implements Data_Base_Connection {

    private static MySQL_Data_Base_Connection instance;
    private String url;
    private String user;
    private String password;

    private MySQL_Data_Base_Connection() {
        getDatabaseProperties();

    }

    public static synchronized MySQL_Data_Base_Connection getInstance() {
        if (instance == null) {
            try {
                instance = new MySQL_Data_Base_Connection();

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