package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private String username;
    private String password;
    private String url;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testpersonne","root", "");

    }

    public static Connection getConnection() throws SQLException {
        if (connection==null) {
            new DBConnection();
        }
        return connection;
    }
}
