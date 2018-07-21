package scanheros.popup;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scanheros.Lanceur;

public class PopupAvancee extends Popup {

    private ProgressBar pBar;
    private ProgressIndicator pInd;
    private Text imp;
    private HBox h;
    private VBox v;
    private Button val;

    public PopupAvancee() {
        super("Running");
        pBar = new ProgressBar(0.0);
        pInd = new ProgressIndicator(0.0);
        imp = new Text("Running");
        h = new HBox(10);
        v = new VBox(10);
        val = new Button("OK");
    }

    public ProgressIndicator getpInd() {
        return pInd;
    }

    public void setpInd(ProgressIndicator pInd) {
        this.pInd = pInd;
    }

    public ProgressBar getpBar() {
        return pBar;
    }

    public void setpBar(ProgressBar pBar) {
        this.pBar = pBar;
    }

    public void start(Stage s) throws Exception {
        super.start(getMyDialog());
        getMyDialog().getIcons()
            .add(new Image(Lanceur.class.getResource("img/exclamation.png").toString()));
        h.setStyle("-fx-alignment:center;");
        v.setStyle("-fx-alignment:center;");
        h.getChildren().addAll(imp);
        v.getChildren().addAll(h, pBar, pInd);
        pane1.setStyle(
            "-fx-background-color:rgb(219,198,211);-fx-padding:10px;-fx-alignment:center;");
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
