package scanheros.popup;

import javafx.scene.control.Dialog;

public class PopupChoixBDD implements Popup {

    private Dialog<String> popup;

    public PopupChoixBDD() {
        popup = new Dialog<>();
        popup.setTitle("Choix de la BDD");
        popup.setContentText("Base de \ndonnées (*.db) :");
    }

    @Override
    public Dialog<String> get() {
        return popup;
    }

}
