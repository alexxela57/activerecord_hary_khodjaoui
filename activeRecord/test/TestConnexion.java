import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestConnexion {
    @Test
    public void testConnexion_meme() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertEquals(c1,c2,"Devraient etre la meme");
        assertEquals(c1.getClass(),c2.getClass(),"devrait etre une connection");

    }

    /**
     * ATTENTION
     * POUR FONCTIONNER, IL FAUT QUE LA BASE DE DONNEE
     * XXX
     * SOIT CREEE
     * @throws SQLException
     */
    @Test
    public void testConnexion_setnom_change() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("XXX");
        Connection c2 = DBConnection.getConnection();
        assertNotEquals(c1,c2,"devraient etre differentes");
    }

    @Test
    public void testConnexion_setnom_meme() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("testpersonne");
        Connection c2 = DBConnection.getConnection();
        assertEquals(c1,c2,"devraient etre la meme");
    }

    @Test
    public void testConnexion_setnom_null() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB(null);
        Connection c2 = DBConnection.getConnection();
        assertEquals(c1,c2,"devraient etre la meme");
    }
}
