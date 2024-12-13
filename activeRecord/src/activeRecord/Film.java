package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    public Film(String titre,Personne p){
        this.titre = titre;
        this.id =-1;
        this.id_real =p.getId();
    }

    private Film(String titre,int id, int id_real){
        this.titre = titre;
        this.id = id;
        this.id_real =id_real;
    }

    public static ArrayList<Film> findAll() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Film f inner join Personne p on f.id_rea = p.id;";

        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        ArrayList<Film> list = new ArrayList<Film>();
        while (rs.next()) {
            String titre = rs.getString("titre");
            int id = rs.getInt("f.id");
            int id_real = rs.getInt("id_rea");
            //System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
            Film film = new Film(titre, id, id_real);
            list.add(film);
        }
        return list;
    }

    public static Film findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
        Film film = null;

        String SQLPrep = "SELECT * FROM Film f inner join Personne p on f.id_rea = p.id;" +
                " WHERE ID = ?";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();

        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_rea");
            film = new Film(titre, id,id_real);
        }
        return film;
    }

    public Personne getRealisateur(int id_real) throws SQLException {
        return Personne.findById(id_real);
    }


    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_real() {
        return id_real;
    }

    public void setId_real(int id_real) {
        this.id_real = id_real;
    }
}
