package scanheros;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scanheros.security.Hardware4Win;

public class Lanceur extends Application {

    public static boolean verify() {
        boolean verif = false;
        for (Entry<Object, Object> prop : System.getProperties().entrySet()) {
            //System.out.println(prop);
        }
        System.out.println(LocalDateTime.now());
        System.out.println(Hardware4Win.getSerialNumber());
        verif = true;
        return verif;
    }

    public static void main(String[] args) {
        if (verify()) {
            launch(args);
        } else {
            System.err.println("NON VERIF");
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = Lanceur.class.getResource("fxml/HeroScanView.fxml");
        System.out.println(Lanceur.class.getResource("fmxl/HeroScanView.fxml"));

        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons()
            .add(new Image(Lanceur.class.getResource("img/glass.png").toString()));
        primaryStage.setTitle("Scanner de Héros Travian v3.0");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }


}
