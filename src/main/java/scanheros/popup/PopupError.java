package scanheros.popup;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scanheros.Lanceur;

public class PopupError extends Popup {

    private Text imp;
    private HBox h;
    private VBox v;
    private Button val;

    public PopupError(String err) {
        super("Error");
        imp = new Text(err);
        h = new HBox(10);
        v = new VBox(10);
        val = new Button("OK");
    }

    public void start(Stage s) throws Exception {
        super.start(getMyDialog());
        getMyDialog().getIcons()
            .add(new Image(Lanceur.class.getResource("img/exclamation.png").toString()));
        h.setStyle("-fx-alignment:center;");
        v.setStyle("-fx-alignment:center;");
        h.getChildren().addAll(imp);
        v.getChildren().addAll(h, val);
        pane1.setStyle("-fx-background-color:#ff8080;-fx-padding:10px;-fx-alignment:center;");
        pane1.getChildren().addAll(v);
        getMyDialog().setWidth(imp.getLayoutBounds().getWidth() + 100);
        getMyDialog().setHeight(150);
        val.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
    }


}
