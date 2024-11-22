import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestPersonne {

    Personne p1 = new Personne("Spielberg","Steven",1);
    Personne p2 = new Personne("Scott","Ridley",2);
    Personne p3 = new Personne("Kubrick","Stanley",3);
    Personne p4 = new Personne("Fincher","David",4);

    @Test
    public void testFindAll() throws SQLException {
        ArrayList<Personne> list = Personne.findAll();
        assertEquals("devraient etre la meme personne",true,list.get(0).equals(p1));
        assertEquals("devraient etre la meme personne",true,list.get(1).equals(p2));
        assertEquals("devraient etre la meme personne",true,list.get(2).equals(p3));
        assertEquals("devraient etre la meme personne",true,list.get(3).equals(p4));
    }

    @Test
    public void testFindById() throws SQLException {
        Personne p = Personne.findById(1);
        assertEquals("devraient etre la meme personne",true,p.equals(p1));
    }


}