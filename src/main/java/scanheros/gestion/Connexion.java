package scanheros.gestion;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import scanheros.popup.PopupAvancee;
import scanheros.popup.PopupError;

public class Connexion {


    private String DBPath = "";
    private Connection connection = null;
    private Statement statement = null;

    public Connexion(String dBPath) {
        DBPath = dBPath;
    }

    /**
     *
     * @param enregistrement
     */
    void addEnregistrement(Enregistrement enregistrement) {
        try {
            PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO Enregistrement VALUES(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, enregistrement.getId());
            preparedStatement.setString(2, enregistrement.getPseudo());
            preparedStatement.setString(3, enregistrement.getCasque());
            preparedStatement.setString(4, enregistrement.getBdroit());
            preparedStatement.setString(5, enregistrement.getBgauche());
            preparedStatement.setString(6, enregistrement.getArmure());
            preparedStatement.setString(7, enregistrement.getBottes());
            preparedStatement.setBoolean(8, enregistrement.isCheval());
            preparedStatement.setInt(9, enregistrement.getExperience());
            preparedStatement.setTimestamp(10, enregistrement.getDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    /**
     * @return la liste des ID trouvés
     */
    public HashMap<String, Enregistrement> getIdFromPseudo(ArrayList<String> listePseudo) {
        HashMap<String, Enregistrement> mapId = new HashMap<>();
        ResultSet resultSet = null;
        for (String pseudo : listePseudo) {
            resultSet = query(
                "SELECT id_joueur,max(pop_vivi) FROM x_world WHERE nom_joueur='" + pseudo + "'");
            try {
                if (resultSet.next()) {
                    mapId.put(pseudo,
                        new Enregistrement(Integer.valueOf(resultSet.getString("id_joueur"))));
                }
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
                new PopupError(e).get().show();
            }
        }
        return mapId;
    }


    /**
     * @return resultat, les donn�es renvoy�es par la BDD
     */
    public ResultSet query(String requet) {
        ResultSet resultat = null;
        try {
            resultat = statement.executeQuery(requet);
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
        return resultat;

    }

    public void insertSqlFile(String cheminFichier) {
        Path chemin_fichier = Paths.get(cheminFichier);
        //int i=0;
        SimpleDoubleProperty avancee = new SimpleDoubleProperty(0.0);
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(cheminFichier)));
            PopupAvancee popAvancee = new PopupAvancee();
            popAvancee.getpBar().progressProperty().bindBidirectional(avancee);
            popAvancee.getpInd().progressProperty().bindBidirectional(avancee);

            popAvancee.get().show();
            System.out.println("File " + cheminFichier + " opened successfully");
            final int taille = Files.readAllLines(chemin_fichier).size();

            Task<Void> run1 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    double i = 0;
                    for (String line : Files.readAllLines(chemin_fichier)) {
                        statement.executeUpdate(line);
                        avancee.set(i / taille);
                        i++;
                    }
                    return null;
                }
            };

            // Launching the Task into a new Thread to separate it of the main
            // of the scanheros.popup
            new Thread(run1).start();

            try {
                run1.stateProperty().addListener((observableValue, oldValue, newValue) -> {
                    switch (newValue) {
                        case FAILED:
                            new PopupError("Task result : " + newValue).get().show();
                            break;
                        case CANCELLED:
                            new PopupError("Task result : " + newValue).get().show();
                            break;
                        case SUCCEEDED:
                            break;
                        default:
                            break;
                    }
                });

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
                new PopupError(e).get().show();
            }

            br.close();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    /**
     *
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
            statement = connection.createStatement();
            Logger.getLogger(getClass().getName())
                .log(Level.INFO, () -> ("** Connexion a " + DBPath + " avec succès **"));
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    public void dropTable(String table) {
        try {
            statement.executeUpdate("DROP TABLE " + table + "");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    public void cleanTable(String table) {
        try {
            statement.executeUpdate("DELETE FROM " + table + "");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    /**
     *
     * @param requete
     */
    public void update(String requete) {
        try {
            statement.executeUpdate(requete);
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    public void close() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
