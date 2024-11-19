import activeRecord.DBConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class TestConnexion {
    @Test
    public void testConnexion() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertEquals("Devraient etre la meme",c1,c2);
        assertEquals("devrait etre une connection","java.sql.Connection",c2.getClass());

    }
}
