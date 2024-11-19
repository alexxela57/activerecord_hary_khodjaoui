package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://db4free.net/testpersonne","scruzlara", "root2014");
    }

    public Connection getConnection() throws SQLException {
        if (connection==null) {
            new DBConnection();
        }
        return connection;
    }
}
