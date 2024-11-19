package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private static String url;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testpersonne","root", "0777973314_Ale");

    }

    public static Connection getConnection() throws SQLException {
        if (connection==null) {
            new DBConnection();
        }
        return connection;
    }

    public void setNomDB(String nomDB){
        this.url = nomDB;
    }
}
