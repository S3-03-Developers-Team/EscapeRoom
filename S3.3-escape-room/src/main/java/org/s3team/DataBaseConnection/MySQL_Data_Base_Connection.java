package org.s3team.DataBaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class MySQL_Data_Base_Connection implements Data_Base_Connection {

    private static MySQL_Data_Base_Connection instance;
    private Connection connection;
    private String url;
    private String user;
    private String password;

    private MySQL_Data_Base_Connection() throws SQLException, IOException, IllegalStateException {
        getDatabaseProperties();
        this.connection = null;

    }


    public static synchronized MySQL_Data_Base_Connection getInstance() throws SQLException, IOException {
        if (instance == null) {
            try {

                instance = new MySQL_Data_Base_Connection();
                instance.openConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Error connecting to the database", e);
            }

        }
        return instance;
    }

    private void getDatabaseProperties() throws IOException {
        Map<String, String> env = System.getenv();
        url = env.get("DB_URL");
        user = env.get("DB_USER");
        password = env.get("DB_PASSWORD");

    }

    @Override
    public void openConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                System.out.println("Connection is closed or null. Attempting to re-establish connection...");
                if (url == null || user == null || password == null) {
                    throw new IllegalStateException("\"Database connection properties (DB_URL, DB_USER, DB_PASSWORD) are missing \" +\n" +
                            "                    \"from environment variables. Check your .env file and docker-compose.yaml.\"");
                }
                this.connection = DriverManager.getConnection(url, user, password);
                System.out.println("Successful connection to MySQL.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }


    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing connection to data base", e);
            }
        }

    }

}
