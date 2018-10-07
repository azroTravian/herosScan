package scanheros.popup;

import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class PopupChoixTpsReload implements Popup {

    private Dialog<Integer> popup;

    public PopupChoixTpsReload() {
        popup = new Dialog<>();
        popup.initStyle(StageStyle.UTILITY);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Information");
        popup.setHeaderText("Choississez le temps de relance");
        popup.setContentText("Quel interval entre chaque vérification ? (60 à ");
    }


    @Override
    public Dialog<Integer> get() {
        return popup;
    }
}
