import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonne {

    Personne p1 = new Personne("Spielberg","Steven",1);
    Personne p2 = new Personne("Scott","Ridley",2);
    Personne p3 = new Personne("Kubrick","Stanley",3);
    Personne p4 = new Personne("Fincher","David",4);

    @BeforeEach
    void ajouter() throws SQLException {
        Personne.createTable();
    }

    @AfterEach
    void supprimer() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    public void testFindAll() throws SQLException {
        ArrayList<Personne> list = Personne.findAll();
        assertEquals(true,list.get(0).equals(p1),"devraient etre la meme personne");
        assertEquals(true,list.get(1).equals(p2),"devraient etre la meme personne");
        assertEquals(true,list.get(2).equals(p3),"devraient etre la meme personne");
        assertEquals(true,list.get(3).equals(p4),"devraient etre la meme personne");
    }

    @Test
    public void testFindById() throws SQLException {
        Personne p = Personne.findById(1);
        assertEquals(true,p.equals(p1),"devraient etre la meme personne");
    }


    @Test
    public void testFindByName() throws SQLException {
        ArrayList<Personne> listPBase = Personne.findByName("Spielberg");
        ArrayList<Personne> listTest = new ArrayList<Personne>();
        listTest.add(p1);

        //comparer la taille des listes
        assertEquals(listTest.size(), listTest.size(),"Les listes doivent être de la même taille");

        for (int i=0; i<listTest.size(); i++){
            Personne pres = listTest.get(i);
            Personne pp = listPBase.get(i);


            assertEquals(true, pres.equals(pp),"les personnes devraient être les memes.");
        }

    }
}