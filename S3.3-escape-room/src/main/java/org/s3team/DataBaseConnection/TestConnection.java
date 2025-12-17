package org.s3team.DataBaseConnection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ClassName: H2_Data_Base_Connection
 * Package: org.s3team.DataBaseConnection
 * Description:
 * Author: Rong Jiang
 * Create:16/12/2025 - 21:51
 * Version:v1.0
 *
 */


    public class TestConnection implements Data_Base_Connection {

        private static TestConnection instance;

        private TestConnection() {}

        public static TestConnection getInstance() {
            if (instance == null) {
                instance = new TestConnection();
            }
            return instance;
        }

    @Override
    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MYSQL", "sa", "");

            try (Statement stmt = conn.createStatement()) {

                stmt.execute("""
                CREATE TABLE IF NOT EXISTS theme (
                    id_theme INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL)""");

                        stmt.execute("""
                MERGE INTO theme (id_theme, name)
                KEY(id_theme)
                VALUES (1, 'Default Theme')""");


                stmt.execute("CREATE TABLE IF NOT EXISTS room (" +
                        "id_room INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "difficulty VARCHAR(50) NOT NULL, " +
                        "price DECIMAL(10,2) NOT NULL, " +
                        "theme_id INT NOT NULL, " +
                        "FOREIGN KEY (theme_id) REFERENCES theme(id_theme))");
            }

            return conn;

        } catch (SQLException e) {
            throw new RuntimeException("Error initializing H2 in-memory database", e);
        }
    }

    @Override
    public void openConnection() {

    }

    @Override
    public void closeConnection() {

    }
}
