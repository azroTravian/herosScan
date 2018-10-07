package scanheros.popup;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import scanheros.Lanceur;

public class PopupIdentification implements Popup {

    private Dialog<Pair<String, Pair<String, String>>> popup;

    public PopupIdentification() {
        popup = new Dialog<>();
        popup.initStyle(StageStyle.UTILITY);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Connexion");
        popup.setHeaderText("Choissisez votre serveur et vos informations de connexions");

        Stage stage = (Stage) popup.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Lanceur.class.getResource("img/lock.png").toString()));

        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        popup.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        ComboBox<String> comboBox = new ComboBox<>();
        try {
            comboBox.getItems().addAll(
                Files.readAllLines(Paths.get(Lanceur.class.getResource("serveurs.txt").toURI())));
        } catch (IOException | URISyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
        grid.add(new Label("Serveur:"), 0, 2);
        grid.add(comboBox, 1, 2);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = popup.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        popup.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

// Convert the result to a username-password-pair when the login button is clicked.
        popup.setResultConverter(dialogButton -> {
            if (dialogButton.equals(loginButtonType)) {
                return new Pair<>(comboBox.getSelectionModel().getSelectedItem(),
                    new Pair<>(username.getText(), password.getText()));
            }
            System.exit(55);
            return null;
        });
    }


    @Override
    public Dialog<Pair<String, Pair<String, String>>> get() {
        return popup;
    }
}
