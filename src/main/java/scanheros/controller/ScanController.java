package scanheros.controller;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import scanheros.exception.MissingCredentialsException;
import scanheros.gestion.Connexion;
import scanheros.gestion.Enregistrement;
import scanheros.gestion.Experience;
import scanheros.gestion.LigneEnre;
import scanheros.gestion.TimerScanTask;
import scanheros.popup.PopupAddHero;
import scanheros.popup.PopupChoixServeur;
import scanheros.popup.PopupChoixTpsReload;
import scanheros.popup.PopupError;
import scanheros.popup.PopupIdentification;

public class ScanController implements Initializable {

    private static String id = null;
    private static String mdp = null;
    private static int tempsReload = 0;

    private static Connexion connexion = null;
    private static String serveur = null;
    private static String bdd = null;
    private static Experience expe = null;
    private ObservableList<LigneEnre> data = FXCollections.observableArrayList();
    private ObservableList<Ligne> listeHero = FXCollections.observableArrayList();

    @FXML
    private BorderPane home;
    @FXML
    private TableView<LigneEnre> table;
    @FXML
    private TableColumn<LigneEnre, Integer> col_id;
    @FXML
    private TableColumn<LigneEnre, String> col_pseudo;
    @FXML
    private TableColumn<LigneEnre, String> col_casque;
    @FXML
    private TableColumn<LigneEnre, String> col_bdroit;
    @FXML
    private TableColumn<LigneEnre, String> col_bgauche;
    @FXML
    private TableColumn<LigneEnre, String> col_armure;
    @FXML
    private TableColumn<LigneEnre, String> col_bottes;
    @FXML
    private TableColumn<LigneEnre, Boolean> col_cheval;
    @FXML
    private TableColumn<LigneEnre, Integer> col_exp;
    @FXML
    private TableColumn<LigneEnre, Timestamp> col_date;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuSQL;
    @FXML
    private MenuItem menuSql1;
    @FXML
    private MenuItem menuSql2;
    @FXML
    private MenuItem menuSql3;
    @FXML
    private Menu menuAjoutHero;
    @FXML
    private MenuItem menuAjPseudo;
    @FXML
    private MenuItem menuAjId;
    @FXML
    private Menu menuCheck;
    @FXML
    private MenuItem menuChOnce;
    @FXML
    private MenuItem menuChAuto;
    @FXML
    private Menu menuSuppr;
    @FXML
    private MenuItem menuSupprSelect;
    @FXML
    private MenuItem menuSupprAll;
    @FXML
    private Menu menuServeur;
    @FXML
    private MenuItem menuChangeServeur;
    @FXML
    private MenuItem menuVoir;
    @FXML
    private TextField cherche_pseudo;
    @FXML
    private Spinner<Integer> sp_heure;
    @FXML
    private Spinner<Integer> sp_minute;
    @FXML
    private Spinner<Integer> sp_seconde;
    @FXML
    private HBox hbox_top;
    @FXML
    private DatePicker pick_date;
    @FXML
    private ToggleButton toggle_filter;

    public static String getServeur() {
        return serveur;
    }

    private static void getFileFromWeb(String nameFile) {
        // Sp�cifier le chemin exact vers le fichier
        URL u;
        try {
            u = new URL(nameFile);
            // Ouvrir la connexion, d�but de la communication avec le serveur
            URLConnection uc = u.openConnection();
            // R�cup�rer la taille exacte en nombre d�octets du fichier d�sign�,
            // et la stocker dans un int
            int taille = uc.getContentLength();
            // Cr�er un flux d�entr�e pour le fichier
            InputStream brut = uc.getInputStream();
            // Mettre ce flux d�entr�e en cache (pour un meilleur transfert,
            // plus s�r et plus r�gulier).
            InputStream entree = new BufferedInputStream(brut);
            // Cr�er une matrice (un tableau) de bytes pour stocker tous les
            // octets du fichier
            byte[] donnees = new byte[taille];
            // Pour l�instant aucun octet n�a encore �t� lu
            int octetsLus = 0;
            // Octets de d�placement, et octets d�j� lus.
            int deplacement = 0;
            float alreadyRead = 0;
            // Boucle permettant de parcourir tous les octets du fichier � lire
            while (deplacement < taille) {
                // utilisation de la methode "read" de la classe InputStream
                octetsLus = entree.read(donnees, deplacement, donnees.length - deplacement);
                // Petit calcul: mise � jour du nombre total d�octets lus par
                // ajout au nombre d�octets lus au cours des pr�c�dents passages
                // au nombre d�octets lus pendant ce passage
                alreadyRead = alreadyRead + octetsLus;
                // -1 marque par convention la fin d�un fichier, double
                // op�rateur "�gale": = =
                if (octetsLus == -1) {
                    break;
                }
                // se cadrer � un endroit pr�cis du fichier pour lire les octets
                // suivants, c�est le d�placement
                deplacement += octetsLus;
            }
            // fermer le flux d�entr�e.
            entree.close();
            // R�cup�rer le nom du fichier
            String fichier = u.getFile();
            fichier = fichier.substring(fichier.lastIndexOf('/') + 1);
            // Cr�er un fichier sur le DD afin d�y copier le contenu du fichier
            // t�l�charg� (par un flux de sortie).
            FileOutputStream fichierSortie = new FileOutputStream(fichier);
            // copier�
            fichierSortie.write(donnees);
            // vider puis fermer le flux de sortie
            fichierSortie.flush();
            fichierSortie.close();
        } catch (IOException e) {
            Logger.getLogger(ScanController.class.getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }

    }

    public static void getImageFromWeb(String imageName, int idHero) {
        try {
            URL site = new URL(serveur + "/hero_body.php?uid=" + idHero);
            File file = new File(imageName);
            boolean creation = file.createNewFile();
            InputStream in = site.openStream();
            OutputStream out = new FileOutputStream(file);
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) != -1) {
                out.write(b, 0, length);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            Logger.getLogger(ScanController.class.getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    public static Connexion getConnexion() {
        return connexion;
    }

    public static Experience getExpe() {
        return expe;
    }

    //--/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //--/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* Popup servant � l'identification, on demande un pseudo et un mot de passe, stock� seulement pendant la session, servira � cc�der aux exp des h�ros*/
        Dialog<Pair<String, Pair<String, String>>> popIdentif = new PopupIdentification().get();
        Pair<String, Pair<String, String>> result = null;
        try {
            result = popIdentif.showAndWait()
                .orElseThrow(MissingCredentialsException::new);
        } catch (MissingCredentialsException e) {
            System.exit(55);
        }

        serveur = result.getKey();
        Pair<String, String> usernamePassword = result.getValue();
        id = usernamePassword.getKey();
        mdp = usernamePassword.getValue();
        expe = new Experience(id, mdp);

        bdd = "TG_table.db";
        connexion = new Connexion(bdd);
        connexion.connect();

        checkAndCreateTables();

        //--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--!!--
        //On v�rifie s'il y a un serveur enregistr�, si non on en demande un, on pourra le modifier plus tard

        try (ResultSet serveurRS = connexion.query("Select * from serveur")) {
            String name4 = null;
            if (serveurRS.next()) {
                name4 = serveurRS.getString("nom");
            }
            if (name4 == null) {
                Dialog<String> popChServeur = new PopupChoixServeur().get();
                final Optional<String> serveurOp = popChServeur.showAndWait();
                selectServeur();
            } else {
                serveur = "https://" + name4;
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }

        ResultSet resultSet = connexion.query("SELECT * FROM Enregistrement");
        try {
            while (resultSet.next()) {
                //				System.out.print("Date : "+resultSet.getTimestamp("date")+" / ");
                //				System.out.print("ID : "+resultSet.getInt("id")+" / ");
                //				System.out.print("Casque : "+resultSet.getString("casque")+" / ");
                //				System.out.print("BDROIT : "+resultSet.getString("BDROIT")+" / ");
                //				System.out.print("BGAUCHE : "+resultSet.getString("BGAUCHE")+" / ");
                //				System.out.print("ARMURE : "+resultSet.getString("ARMURE")+" / ");
                //				System.out.print("BOTTES : "+resultSet.getString("BOTTES")+" / ");
                //				System.out.print("CHEVAL : "+resultSet.getBoolean("CHEVAL")+" / ");
                //				System.out.println("date : "+resultSet.getTimestamp("date"));
                data.addAll(new LigneEnre(
                    new Enregistrement(resultSet.getInt("id"), resultSet.getString("Pseudo"),
                        resultSet.getString("Casque"), resultSet.getString("bdroit"),
                        resultSet.getString("bgauche"), resultSet.getString("armure"),
                        resultSet.getString("bottes"), resultSet.getBoolean("cheval"),
                        resultSet.getInt("experience"), resultSet.getTimestamp("date"))));
            }
            resultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }

        /* Conditions sur les colonnes pour l'ajout des donn�es*/
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_pseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        col_casque.setCellValueFactory(new PropertyValueFactory<>("casque"));
        col_bdroit.setCellValueFactory(new PropertyValueFactory<>("bdroit"));
        col_bgauche.setCellValueFactory(new PropertyValueFactory<>("bgauche"));
        col_armure.setCellValueFactory(new PropertyValueFactory<>("armure"));
        col_bottes.setCellValueFactory(new PropertyValueFactory<>("bottes"));
        col_cheval.setCellValueFactory(new PropertyValueFactory<>("cheval"));
        col_exp.setCellValueFactory(new PropertyValueFactory<>("experience"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        col_id.setStyle("-fx-alignment:center;");
        col_cheval.setStyle("-fx-alignment:center;");
        table.setItems(data);

        table.getColumns().get(0).setVisible(false);
        table.getColumns().get(0).setVisible(true);
        col_date.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(col_date);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        pick_date.setValue(LocalDate.now());
        ObjectBinding<LocalDateTime> dateTime = Bindings.createObjectBinding(() ->
                LocalDateTime.of(pick_date.getValue(),
                    LocalTime.of(sp_heure.getValue(), sp_minute.getValue(), sp_seconde.getValue())),
            pick_date.valueProperty(), sp_heure.valueProperty(), sp_minute.valueProperty(),
            sp_seconde.valueProperty());

        FilteredList<LigneEnre> filteredData = new FilteredList<>(data, p -> true);
        SortedList<LigneEnre> sortedData = new SortedList<>(filteredData);

        cherche_pseudo.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(choiceLine -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //Compare si le texte est dans le pseudo ET si le bouton filter est coch� il s'occupe aussi de la date
                String lowerCaseFilter = newValue.toLowerCase();
                if (toggle_filter.isSelected()) {
                    return choiceLine.getPseudo().toLowerCase().contains(lowerCaseFilter)
                        && choiceLine.getDate().toLocalDateTime().isAfter(dateTime.get());
                } else {
                    return choiceLine.getPseudo().toLowerCase().contains(lowerCaseFilter);
                }

            });
        });

        ChangeListener<Object> changeListener =
            (observable, oldValue, newValue) -> {
                filteredData.setPredicate(choiceLine -> {
                    if (newValue == null) {
                        return true;
                    }

                    //Compare si le texte est dans le pseudo ET si le bouton filter est coch� il s'occupe aussi de la date
                    String lowerCaseFilter = cherche_pseudo.getText().toLowerCase();
                    if (toggle_filter.isSelected()) {
                        return choiceLine.getPseudo().toLowerCase().contains(lowerCaseFilter)
                            && choiceLine.getDate().toLocalDateTime().isAfter(dateTime.get());
                    } else {
                        return choiceLine.getPseudo().toLowerCase().contains(lowerCaseFilter);
                    }

                });
            };

        dateTime.addListener(changeListener);
        toggle_filter.selectedProperty().addListener(changeListener);

        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    void once_onclick(ActionEvent event) {
        TimerTask TimerScanTask = new TimerScanTask(listeHero, this);
        new Thread(TimerScanTask).start();
    }

    @FXML
    void auto_onclick(ActionEvent event) {
        if (tempsReload == 0) {
            new PopupChoixTpsReload().get().showAndWait().ifPresent(val -> {
                if (val > (listeHero.size()) && val < 12000) {
                    tempsReload = val;
                    TimerTask TimerScanTask = new TimerScanTask(listeHero, this);
                    Timer timer = new Timer();
                    timer.schedule(TimerScanTask, 0, tempsReload * 1000);
                } else {
                    new PopupError(
                        "Minimum : taille de la liste de héros (1 sec/héros) \nMaximum : 20min (1200sec)")
                        .get().show();
                }
            });
        } else {
            TimerTask TimerScanTask = new TimerScanTask(listeHero, this);
            Timer timer = new Timer();
            timer.schedule(TimerScanTask, 0, tempsReload);
        }
    }

    @FXML
    void ajid_onclick(ActionEvent event) {
        PopupAddHero popAdHero = new PopupAddHero(listeHero, "Id", connexion);
        popAdHero.get().showAndWait();
    }

    @FXML
    void ajpseudo_onclick(ActionEvent event) {
        PopupAddHero popAdHero = new PopupAddHero(listeHero, "Pseudo", connexion);
        popAdHero.get().showAndWait();
    }

    @FXML
    void sql1_onclick(ActionEvent event) {
        if (serveur != null) {
            System.out.println(serveur);
            getFileFromWeb(serveur + "/" + "map.sql");
        } else {
            new PopupError("Vous n'avez pas choisi le serveur").get().show();
        }
    }

    @FXML
    void sql2_onclick(ActionEvent event) {
        connexion.cleanTable("x_world");
        connexion.insertSqlFile("map.sql");
    }

    @FXML
    void sql3_onclick(ActionEvent event) {
        sql1_onclick(event);
        sql2_onclick(event);
    }

    @FXML
    void supprSelect_onclick(ActionEvent event) {
        ObservableList<LigneEnre> selected = table.getSelectionModel().getSelectedItems();
        for (LigneEnre ligneEnre : selected) {
            System.out.println(ligneEnre.getId() + " " + ligneEnre.getDate().getTime());
            connexion.update(
                "DELETE FROM Enregistrement WHERE id=" + ligneEnre.getId() + " AND date="
                    + ligneEnre.getDate().getTime());
            data.remove(ligneEnre);
        }
    }

    @FXML
    void supprAll_onclick(ActionEvent event) {
        connexion.cleanTable("Enregistrement");
        data.clear();
    }

    @FXML
    void changeServ_onclick(ActionEvent event) {
        selectServeur();
    }

    @FXML
    void voirServ_onclick(ActionEvent event) {
        try {
            URI uri = URI.create(serveur);
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }

    private void selectServeur() {
        Dialog<String> popChServeur = new PopupChoixServeur().get();

        final Optional<String> serveurOp = popChServeur.showAndWait();

        serveurOp.ifPresent(s -> {
            serveur = "http://" + s;
            try {
                PreparedStatement p = connexion.getConnection()
                    .prepareStatement("UPDATE serveur SET nom = ?");
                p.setString(1, s);
                p.executeUpdate();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
                new PopupError(e).get().show();
            }
        });
    }

    private void checkAndCreateTables() {
        //TABLE X_WORLD
        connexion.update(
            "CREATE TABLE  IF NOT EXISTS  'x_world' (	'id_case'	INTEGER,'x'	INTEGER,'y'	INTEGER,'peuple'	INTEGER,'id_vivi'	INTEGER,'nom_vivi'	TEXT,"
                + "	'id_joueur'	INTEGER,'nom_joueur'	TEXT,'id_alliance'	INTEGER,'nom_alliance'	TEXT,'pop_vivi'	INTEGER,PRIMARY KEY('id_vivi'))");

        //TABLE ENREGISTREMENT
        connexion.update(
            "CREATE TABLE  IF NOT EXISTS  'Enregistrement' ( 'ID' INT NOT NULL, 'Pseudo' TEXT NOT NULL, 'Casque' TEXT NOT NULL, 'Bdroit' TEXT NOT NULL,"
                + " 'Bgauche' TEXT NOT NULL,'Armure' TEXT NOT NULL, 'Bottes' TEXT NOT NULL, 'Cheval' TEXT NOT NULL, "
                + "'Experience' INTEGER NOT NULL, 'Date' TIMESTAMP NOT NULL, PRIMARY KEY('ID','Date') )");

        //TABLE SERVEUR
        connexion.update(
            "CREATE TABLE IF NOT EXISTS  `serveur` ( `nom` TEXT NOT NULL UNIQUE, PRIMARY KEY(`nom`) )");
    }

    public ObservableList<LigneEnre> getData() {
        return data;
    }
}
