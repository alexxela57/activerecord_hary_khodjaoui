import activeRecord.DBConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestConnexion {
    @Test
    public void testConnexion_meme() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertEquals("Devraient etre la meme",c1,c2);
        assertEquals("devrait etre une connection",c1.getClass(),c2.getClass());

    }

    public void testConnexion_setnom_change() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("jdbc:mysql://127.0.0.1:3306/XXXX");
        Connection c2 = DBConnection.getConnection();
        assertNotEquals("devraient etre differentes",c1,c2);
    }

    public void testConnexion_setnom_meme() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("jdbc:mysql://127.0.0.1:3306/testpersonne");
        Connection c2 = DBConnection.getConnection();
        assertEquals("devraient etre la meme",c1,c2);
    }

    public void testConnexion_setnom_null() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB(null);
        Connection c2 = DBConnection.getConnection();
        assertNotEquals("devraient etre la meme",c1,c2);
    }
}
