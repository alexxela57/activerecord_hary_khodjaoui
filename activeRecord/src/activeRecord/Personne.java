package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {

    private int id;
    private String nom;
    private String prenom;


    public Personne (String n, String p){
        id=-1;
        nom=n;
        prenom=p;
    }




    public static ArrayList<Personne> findAll() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne;";

        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        ArrayList<Personne> list = new ArrayList<Personne>();
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            //System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
            Personne personne = new Personne(nom, prenom);
            personne.id = id;
            list.add(personne);
        }
        return list;
    }


    public static Personne findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
        Personne personne = null;

        String SQLPrep = "SELECT * FROM Personne WHERE ID = ?";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();

        if (rs.next()) {
            String nom = rs.getString("NOM");
            String prenom = rs.getString("PRENOM");
            personne = new Personne(nom, prenom);
        }
        return personne;
    }



    public static ArrayList<Personne> findByName(String nom) throws SQLException {
        Connection connect = DBConnection.getConnection();
        ArrayList<Personne> personnes = new ArrayList<>();

        String SQLPrep = "SELECT * FROM Personne WHERE NOM = ?";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setString(1, nom);
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("ID");
            String prenom = rs.getString("PRENOM");
            Personne personne = new Personne( nom, prenom);
            personnes.add(personne);
        }
        return personnes;
    }




}
