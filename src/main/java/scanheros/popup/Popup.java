package scanheros.popup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Popup extends Application {

	private final Stage myDialog;
	String title;
	FlowPane pane1;

	public Popup(String name) {
		title = name;
		myDialog = new Stage();
	}

	@Override
	public void start(Stage myDialog) throws Exception {
		myDialog.setAlwaysOnTop(true);
		pane1 = new FlowPane();
		pane1.setStyle("-fx-background-color:#d6e0f5;-fx-padding:10px;-fx-margin:10px;-fx-alignment:center;");
		Scene scene = new Scene(pane1, 500, 500);
		myDialog.centerOnScreen();
		myDialog.setScene(scene);
		myDialog.setTitle(title);
		myDialog.show();
	}

	public Stage getMyDialog() {
		return myDialog;
	}

	public String getTitle() {
		return title;
	}

	public FlowPane getPane1() {
		return pane1;
	}

}