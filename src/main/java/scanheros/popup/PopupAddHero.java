package scanheros.popup;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scanheros.Lanceur;
import scanheros.gestion.Connexion;
import scanheros.scan.controller.Ligne;


public class PopupAddHero extends Popup {

    private final ObservableList<Ligne> data;
    private Connexion connexion;
    private String type;
    private TableView<Ligne> table;
    private TableColumn<Ligne, String> col1;
    private TableColumn<Ligne, Integer> col2;
    private HBox hb1, hb2, hb3, hb4;
    private VBox vb;
    private Text tx1, tx2;
    private TextField tf1, tf2;
    private ObservableList<Ligne> fnlist;
    private ObservableList<Ligne> d;


    public PopupAddHero(ObservableList<Ligne> d, String type, Connexion connex) {
        super("Ajout d'un héros - " + type);
        table = new TableView<>();
        fnlist = FXCollections.observableArrayList();
        col1 = new TableColumn<>("Pseudo");
        col2 = new TableColumn<>("ID");

        tx1 = new Text("listeHPseudo.txt :  ");
        tx2 = new Text("listeHId.txt :  ");
        hb1 = new HBox(3);
        hb2 = new HBox(3);
        hb3 = new HBox(3);
        hb4 = new HBox(3);
        vb = new VBox(10);
        data = FXCollections.observableArrayList();
        tf1 = new TextField();
        tf2 = new TextField();

        this.d = d;
        this.type = type;
        connexion = connex;
        data.addAll(this.d);
    }

    public boolean running() {
        return true;
    }

    public ObservableList<Ligne> getFnList() {
        return fnlist;
    }

    @SuppressWarnings("unchecked")
    public void start(Stage s) throws Exception {
        super.start(getMyDialog());
        getMyDialog().getIcons()
            .add(new Image(Lanceur.class.getResource("img/itemcasque.png").toString()));
        pane1.setStyle(
            "-fx-background-color:#e6ffff;-fx-padding:10px;-fx-margin:10px;-fx-alignment:center;");
        pane1.getChildren().add(table);
        col1.setMinWidth(180);
        col1.setPrefWidth(180);
        col2.setMinWidth(120);
        col2.setPrefWidth(120);
        table.setPrefWidth(300);
        getMyDialog().setWidth(/*table.getWidth()*/380);
        getMyDialog().setHeight(600);
        table.setEditable(false);

        col1.setCellValueFactory(new PropertyValueFactory<>("pseudo"));

        //col1.setCellFactory(TextFieldTableCell.<Pair<String,Integer>, String>forTableColumn(new DoubleStringConverter()));

        col1.setOnEditCommit(
            new EventHandler<CellEditEvent<Ligne, String>>() {
                @Override
                public void handle(CellEditEvent<Ligne, String> t) {
                    ((Ligne) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                    ).setPseudo(t.getNewValue());
                }
            }
        );

        col2.setCellValueFactory(new PropertyValueFactory<>("id"));

        //col2.setCellFactory(TextFieldTableCell.<Pair<String,Integer>, Double>forTableColumn(new DoubleStringConverter()));

        col2.setOnEditCommit(
            t -> t.getTableView().getItems().get(t.getTablePosition().getRow())
                .setId(t.getNewValue())
        );

        table.setItems(data);
        table.getColumns().addAll(col1, col2);

        tf1.setPromptText(type);
        tf2.setPromptText(type);
        tf1.setMaxWidth(col1.getPrefWidth());
        tf2.setMaxWidth(col1.getPrefWidth());

        //On rend possible uniquement l'ajout de chiffres et d'une virgule dans les champs de texte
        tf1.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = tf1.getText().charAt(oldValue.intValue());
                    // Check if the new character is the number or other's
                    //il ne faut que des chiffres, pas de point, et inf�rieur � 99999
                    if (!((ch >= '0' && ch <= '9') || Integer.valueOf(tf1.getText()) > 99999)) {
                        // if it's not number then just setText to previous one
                        tf1.setText(tf1.getText().substring(0, tf1.getText().length() - 1));
                    }
                }
            }
        });

        tf2.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {

            }
        });

        final Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            if (type == "Pseudo") {
                if (!tf2.getText().isEmpty()) {
                    String idres = "";
                    ResultSet resultSet = connexion.query(
                        "SELECT id_joueur,max(pop_vivi) FROM x_world WHERE nom_joueur='" + tf2
                            .getText() + "'");
                    try {
                        if (resultSet.next()) {
                            idres = resultSet.getString("id_joueur");
                        }
                    } catch (SQLException e) {
                        System.err.println("---  ERREUR  ---");
                        e.printStackTrace();
                    }
                    //On v�rifie que la ligne n'est pas d�j� pr�sente, si oui inutile de l'ajouter
                    if (idres != null &&
                        !d.contains(new Ligne(tf2.getText(), Integer.valueOf(idres))) &&
                        !data.contains(new Ligne(tf2.getText(), Integer.valueOf(idres)))) {
                        data.add(new Ligne(tf2.getText(), Integer.valueOf(idres)));
                    }
                }
            } else if (type == "Id") {
                if (!tf1.getText().isEmpty()) {
                    String psres = "";
                    ResultSet resultSet = connexion.query(
                        "SELECT nom_joueur,max(pop_vivi) FROM x_world WHERE id_joueur=" + Integer
                            .parseInt(tf1.getText()) + "");
                    try {
                        if (resultSet.next()) {
                            psres = resultSet.getString("nom_joueur");
                        }
                    } catch (SQLException e) {
                        System.err.println("---  ERREUR  ---");
                        e.printStackTrace();
                    }
                    //On v�rifie que la ligne n'est pas d�j� pr�sente, si oui inutile de l'ajouter
                    if (!d.contains(new Ligne(psres, Integer.valueOf(tf1.getText()))) &&
                        !data.contains(new Ligne(psres, Integer.valueOf(tf1.getText()))) &&
                        psres != null) {
                        data.add(new Ligne(psres, Integer.valueOf(tf1.getText())));
                    }
                }
            }

            tf1.clear();
            tf2.clear();


        });

        final Button openButtonPseudo = new Button("Ouvrir");
        openButtonPseudo.setOnAction(event -> {
            File file = new File("listeHPseudo.txt");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                PopupError err = new PopupError("Read error : " + e.getMessage());
                Stage s2 = new Stage();
                try {
                    err.start(s2);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        final Button updateButtonPs = new Button("MaJ");
        updateButtonPs.setOnAction(event -> {
            File file = new File("listeHPseudo.txt");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                //read all the lines, and iterate over it
                for (String line : Files
                    .readAllLines(Paths.get("listeHPseudo.txt"), Charset.forName("Cp1252"))) {
                    String idres = "";
                    ResultSet resultSet = connexion.query(
                        "SELECT id_joueur,max(pop_vivi) FROM x_world WHERE nom_joueur='" + line
                            + "'");
                    try {
                        if (resultSet.next()) {
                            idres = resultSet.getString("id_joueur");
                        }
                    } catch (SQLException e) {
                        System.err.println("---  ERREUR  ---");
                        e.printStackTrace();
                    }
                    //On v�rifie que la ligne n'est pas d�j� pr�sente, si oui inutile de l'ajouter
                    if (idres != null &&
                        !d.contains(new Ligne(line, Integer.valueOf(idres))) &&
                        !data.contains(new Ligne(line, Integer.valueOf(idres)))) {
                        data.add(new Ligne(line, Integer.valueOf(idres)));
                    }
                }

                //	br.close();
            } catch (IOException e) {
                e.printStackTrace();
                PopupError err = new PopupError("Read error : " + e.getMessage());
                Stage s2 = new Stage();
                try {
                    err.start(s2);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

        });

        final Button openButtonId = new Button("Ouvrir");
        openButtonId.setOnAction(event -> {
            File file = new File("listeHId.txt");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                PopupError err = new PopupError("Read error : " + e.getMessage());
                Stage s2 = new Stage();
                try {
                    err.start(s2);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        final Button updateButtonId = new Button("Maj");
        updateButtonId.setOnAction(event -> {
            File file = new File("listeHId.txt");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                //read all the lines, and iterate over it
                for (String line : Files.readAllLines(Paths.get("listeHId.txt"))) {

                    String psres = "";
                    ResultSet resultSet = connexion.query(
                        "SELECT nom_joueur,max(pop_vivi) FROM x_world WHERE id_joueur=" + Integer
                            .parseInt(line) + "");
                    try {
                        if (resultSet.next()) {
                            psres = resultSet.getString("nom_joueur");
                        }
                    } catch (SQLException e) {
                        System.err.println("---  ERREUR  ---");
                        e.printStackTrace();
                    }
                    //On v�rifie que la ligne n'est pas d�j� pr�sente, si oui inutile de l'ajouter
                    if (!d.contains(new Ligne(psres, Integer.valueOf(line))) &&
                        !data.contains(new Ligne(psres, Integer.valueOf(line))) &&
                        psres != null) {
                        data.add(new Ligne(psres, Integer.valueOf(line)));
                    }
                }
                //	br.close();
            } catch (IOException e) {
                PopupError err = new PopupError("Read error : " + e.getMessage());
                Stage s2 = new Stage();
                try {
                    err.start(s2);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }


        });

        final Button supprButton = new Button("Supprimer");
        supprButton.setOnAction(event -> {
            Ligne selected1 = table.getSelectionModel().getSelectedItem();
            ObservableList<Ligne> selected = table.getSelectionModel().getSelectedItems();
            if (selected.size() == 1) {
                data.remove(selected1);
            }
        });

        final Button okButton = new Button("OK");
        okButton.setOnAction(actEvent -> {
            for (Ligne ligne : data) {
                if (!d.contains(ligne)) {
                    fnlist.add(ligne);
                }
            }
            d.addAll(fnlist);

            //fermeture de la scanheros.popup
            Node source = (Node) actEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });

        if (Objects.equals(type, "Pseudo")) {
            hb1.getChildren().addAll(tf2, addButton);
            hb1.setAlignment(Pos.CENTER);
            hb4.getChildren().addAll(tx1, openButtonPseudo, updateButtonPs);
        } else if (Objects.equals(type, "Id")) {
            hb1.getChildren().addAll(tf1, addButton);
            hb1.setAlignment(Pos.CENTER);
            hb4.getChildren().addAll(tx2, openButtonId, updateButtonId);
        }

        hb2.getChildren().addAll(supprButton, okButton);
        hb2.setAlignment(Pos.CENTER);

        hb4.setAlignment(Pos.CENTER);

        hb3.getChildren().add(supprButton);
        hb3.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(hb1, hb4, hb3, hb2);
        vb.setAlignment(Pos.CENTER);
        pane1.getChildren().add(vb);


    }


}