package org.s3team.DataBaseConnection;

import java.sql.Connection;

public interface Data_Base_Connection {
    void openConnection();
    Connection getConnection();
    void closeConnection();
}
