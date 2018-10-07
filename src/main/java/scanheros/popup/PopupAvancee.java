package scanheros.popup;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PopupAvancee implements Popup {

    private Alert popup;

    private ProgressBar pBar;
    private ProgressIndicator pInd;

    public PopupAvancee() {
        popup = new Alert(AlertType.INFORMATION);
        pBar = new ProgressBar(0.0);
        pInd = new ProgressIndicator(0.0);
        Text imp = new Text("Running");
        HBox h = new HBox(10);
        h.getChildren().addAll(imp, pBar, pInd);
        popup.getDialogPane().setContent(h);
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


    @Override
    public Dialog get() {
        return popup;
    }
}
