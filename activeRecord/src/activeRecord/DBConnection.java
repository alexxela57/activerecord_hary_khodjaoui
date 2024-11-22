package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;
    private static String dbName = "testpersonne";
    private final static String portNumber = "3306";
    private final static String serverName = "127.0.0.1";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"+serverName+":"+portNumber+"/"+dbName,"root", "");
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection==null) {
            new DBConnection();
        }
        return connection;
    }

    public static void setNomDB(String nom) throws SQLException {
        if (nom!=null && !nom.equals(dbName)){
            dbName=nom;
            connection = null;
        }
    }
}
