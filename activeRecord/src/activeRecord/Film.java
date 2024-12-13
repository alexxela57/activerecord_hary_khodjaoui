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

        String SQLPrep = "SELECT * FROM Film f inner join Personne p on f.id_rea = p.id" +
                " WHERE f.ID = ?;";
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

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String SQLPrep =  """
            CREATE TABLE IF NOT EXISTS film (
                id INT(11) NOT NULL AUTO_INCREMENT,
                titre VARCHAR(40) NOT NULL,
                id_rea int(11) default NULL,
                PRIMARY KEY (id)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1;""";
        try (PreparedStatement prep = connection.prepareStatement(SQLPrep)) {
            prep.executeUpdate();
        }

    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();

        String SQLPrep = "DROP TABLE IF EXISTS film";
        try (PreparedStatement prep = connect.prepareStatement(SQLPrep)) {
            prep.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        Connection connect = DBConnection.getConnection();

        String SQLPrep = "DELETE FROM film WHERE id_rea = ?";
        try (PreparedStatement prep = connect.prepareStatement(SQLPrep)) {
            prep.setInt(1, this.id);
            prep.executeUpdate();
        }

        this.id = -1;
    }

    public void save() throws SQLException, RealisateurAbsentException {
        Connection connect = DBConnection.getConnection();

        ArrayList<Personne> personnes = Personne.findAll();
        boolean existe = false;

        // Vérification si le réalisateur existe dans la liste
        for (Personne personne : personnes) {
            if (personne.getId() == this.id_real) {
                existe = true;
                break;
            }
        }
        if(!existe) {
            throw  new RealisateurAbsentException();
        }

        if (this.id == -1) {
            String SQLPrep = "INSERT INTO film (titre, id_rea) VALUES (?, ?)";
            try (PreparedStatement prep = connect.prepareStatement(SQLPrep, PreparedStatement.RETURN_GENERATED_KEYS)) {
                prep.setString(1, this.titre);
                prep.setInt(2, this.id_real);
                prep.executeUpdate();

                try (ResultSet rs = prep.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            }
        } else {
                String SQLPrep = "UPDATE film SET titre = ?, id_real = ? WHERE f.id = ?";
            try (PreparedStatement prep = connect.prepareStatement(SQLPrep)) {
                prep.setString(1, this.titre);
                prep.setInt(2, this.id_real);
                prep.setInt(3, this.id);
                prep.executeUpdate();
            }
        }
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
