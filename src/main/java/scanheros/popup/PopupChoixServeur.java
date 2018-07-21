package scanheros.popup;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scanheros.Lanceur;

public class PopupChoixServeur extends Popup {

    private VBox v;
    private Text imp;
    private TextField tf1;
    private Button val;
    private HBox h;

    public PopupChoixServeur() {
        super("Choix du serveur");
        imp = new Text(" Serveur : \n(ex : ts2.travian.fr)");
        tf1 = new TextField();

        val = new Button("Valider");
        h = new HBox(5);

        v = new VBox(10);
    }

    public void start(Stage s) throws Exception {
        super.start(getMyDialog());
        getMyDialog().getIcons()
            .add(new Image(Lanceur.class.getResource("img/settings.png").toString()));
        tf1.setPrefWidth(100);
        h.setStyle("-fx-alignment:center;");
        v.setStyle("-fx-alignment:center;");
        h.getChildren().addAll(imp, tf1);
        v.getChildren().addAll(h, val);
        //pane1.setStyle("-fx-background-color:rgb(201,225,206);-fx-padding:10px;-fx-alignment:center;");
        pane1.getChildren().addAll(v);
        getMyDialog().setWidth(tf1.getPrefWidth() + imp.getLayoutBounds().getWidth() + 150);
        getMyDialog().setHeight(150);
    }

    public Button getButton() {
        return val;
    }

    public TextField getText1() {
        return tf1;
    }

}
