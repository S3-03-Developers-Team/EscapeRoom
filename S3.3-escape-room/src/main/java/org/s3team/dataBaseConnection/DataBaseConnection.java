package org.s3team.dataBaseConnection;

import java.sql.Connection;

public interface DataBaseConnection {
    void openConnection();
    Connection getConnection();
    void closeConnection();
}
