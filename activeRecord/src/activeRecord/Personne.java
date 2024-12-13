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

    /**
     * constructeur utilisé dans les tests uniquement
     * @param n
     * @param p
     * @param id
     */
    public Personne (String n, String p, int id){
        this.id=id;
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
            personne.id = id;
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
            personne.id=id;
            personnes.add(personne);
        }
        return personnes;
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String SQLPrep = """
            CREATE TABLE IF NOT EXISTS personne (
                id INT(11) NOT NULL AUTO_INCREMENT,
                nom VARCHAR(40) NOT NULL,
                prenom VARCHAR(40) NOT NULL,
                PRIMARY KEY (id)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1;""";
        try (PreparedStatement prep = connection.prepareStatement(SQLPrep)) {
            prep.executeUpdate();
        }
        SQLPrep= """
            INSERT INTO `Personne` (`id`, `nom`, `prenom`) VALUES
            (1, 'Spielberg', 'Steven'),
            (2, 'Scott', 'Ridley'),
            (3, 'Kubrick', 'Stanley'),
            (4, 'Fincher', 'David');
            """;
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

        SQLPrep = "DROP TABLE IF EXISTS personne";
        try (PreparedStatement prep = connect.prepareStatement(SQLPrep)) {
            prep.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        Connection connect = DBConnection.getConnection();

        String SQLPrep = "DELETE FROM film WHERE id_rea=?";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, this.id);
        prep.executeUpdate();

        SQLPrep = "DELETE FROM personne WHERE id=?";
        prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, this.id);
        prep.executeUpdate();
    }

    public void save() throws SQLException {
        Connection connect = DBConnection.getConnection();

        if (this.id == -1) {
            String SQLPrep = "INSERT INTO personne (nom, prenom) VALUES (?, ?)";
            try (PreparedStatement prep = connect.prepareStatement(SQLPrep, PreparedStatement.RETURN_GENERATED_KEYS)) {
                prep.setString(1, this.nom);
                prep.setString(2, this.prenom);
                prep.executeUpdate();

                try (ResultSet rs = prep.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            }
        } else {
            String SQLPrep = "UPDATE personne SET nom = ?, prenom = ? WHERE id = ?";
            try (PreparedStatement prep = connect.prepareStatement(SQLPrep)) {
                prep.setString(1, this.nom);
                prep.setString(2, this.prenom);
                prep.setInt(3, this.id);
                prep.executeUpdate();
            }
        }
    }



    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    public boolean equals(Personne p) {
        return this.nom.equals(p.nom) && this.prenom.equals(p.prenom) && this.id == p.id;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
