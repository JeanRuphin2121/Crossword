package aka.salako.crossword;



import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Database {
    private Connection connexion;
    public Database(){
        try {
            connexion = connecterBD();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnexion() {
        return connexion;
    }

    public  static Connection connecterBD() throws SQLException {
        Connection connect;
        String url = "jdbc:mysql://localhost/base";
        String username = "root";
        String password = "";
        connect = DriverManager.getConnection(url, username, password);
        return connect;

    }

    public Map<Integer, String> chargerGrids() throws SQLException {
        Map<Integer, String> grids  = new HashMap<>();
        String query = "SELECT * FROM GRID";
        Statement statement = connexion.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String nomGrille = rs.getString("nom_grille");
            Integer numeroGrille = rs.getInt("numero_grille");
            int hauteur = rs.getInt("hauteur");
            int largeur = rs.getInt("largeur");
            grids.put(numeroGrille, nomGrille + " ("+ hauteur + "x" + largeur +")");
        }
        return grids;
    }
    public List<Clue> chargerIndicesHorizontaux(int choix) throws SQLException {
        List<Clue> indicesHorizontaux = new ArrayList<>();
        String query = "SELECT * FROM crossword WHERE numero_grille=" + (choix+1) + " AND horizontal = 1";
        Statement statement = connexion.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String definition = rs.getString("definition");
            int ligne = rs.getInt("ligne");
            int colonne = rs.getInt("colonne");
            indicesHorizontaux.add(new Clue(definition, ligne, colonne, true));
        }
        return indicesHorizontaux;
    }

    public List<Clue> chargerIndicesVerticaux(int choix) throws SQLException {
        List<Clue> indicesVerticaux = new ArrayList<>();
        String query = "SELECT * FROM crossword WHERE numero_grille =" + (choix+1)+ " AND horizontal = 0";
        Statement statement = connexion.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String definition = rs.getString("definition");
            int ligne = rs.getInt("ligne");
            int colonne = rs.getInt("colonne");
            indicesVerticaux.add(new Clue(definition, ligne, colonne, false));
        }
        return indicesVerticaux;
    }


}