package org.s3team.DataBaseConnection;

import java.sql.Connection;

public interface Data_Base_Connection {
    void openConnecttion();
    Connection getConnection();
    void closeConnecttion();
}
