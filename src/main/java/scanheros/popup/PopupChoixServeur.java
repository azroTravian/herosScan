package scanheros.popup;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import scanheros.Lanceur;

public class PopupChoixServeur implements Popup {

    private ChoiceDialog<String> popup;


    public PopupChoixServeur() {

        List<String> choices = null;
        try {
            choices = Files
                .readAllLines(Paths.get(Lanceur.class.getResource("serveurs.txt").toURI()));
        } catch (IOException | URISyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
        }
        popup = new ChoiceDialog<>(null, choices);
        popup.initStyle(StageStyle.UTILITY);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Information");
        popup.setHeaderText("Choississez votre serveur");
        popup.setContentText("Sur quel serveur vous voulez-vous travailler ?");
    }

    @Override
    public Dialog<String> get() {
        return popup;
    }
}
