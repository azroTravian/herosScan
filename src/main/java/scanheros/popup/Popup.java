package scanheros.popup;

import javafx.scene.control.Dialog;

public interface Popup<T> {

    Dialog<T> get();
}
