package scanheros.popup;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class PopupError implements Popup {

    private Alert alert;


    public PopupError(String err) {
        alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle("Erreur");
        alert.setContentText(err);
    }

    public PopupError(Exception ex) {
        alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle("Erreur");
        alert.setContentText(ex.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    @Override
    public Dialog<?> get() {
        return alert;
    }
}
