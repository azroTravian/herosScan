package scanheros.popup;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PopupIdentification extends Popup{
	// on s'en fout un peu que ce soit dans le start ou en attribut SAUF pour les champs de texte et le bouton valider. 
	// Pour recuperer le texte il faut l'avoir en attribut et faire un getter (cf ci-dessous), idem pour le bouton.
	Label imp;
	Label imp2;
	Label aide;
	GridPane grid;
	TextField tf1;
	TextField tf2;
	PasswordField passfield;
	Button val;
	HBox h;
	HBox h2;
	VBox v,v2,v3;
	
	public PopupIdentification(){
		super("Identification");
		imp = new Label("Pseudo :\n");
		tf1 = new TextField();
		imp2 = new Label("Mot de passe : \n");
		tf2 = new TextField();
		passfield = new PasswordField();
		aide = new Label("Ni le pseudo ni le mdp ne sont stock�s et seront demand�s � chaque ouverture \n"
				+ "Ne seront pas non plus v�rifi�s le pseudo + mdp, si il y a une erreur \n"
				+ "cela se remarquera lors de la recherche de l'exp�rience qui ne fonctionnera pas");
		val = new Button("Valider");
		h = new HBox(5);
		h2 = new HBox(5);
		v = new VBox(10);
		v2 = new VBox(10);
		v3 = new VBox(10);
		grid = new GridPane();
	}

	
	public void start(Stage s) throws Exception {
		super.start(getMyDialog());
		getMyDialog().getIcons().add(new Image("file:ressources/lock.png"));
		tf1.setPrefWidth(100);
		tf2.setPrefWidth(100);
		h.setStyle("-fx-alignment:center;");
		v3.setStyle("-fx-alignment:center;");
		v.setStyle("-fx-alignment:center;");
		v2.setStyle("-fx-alignment:center;");
		grid.setStyle("-fx-alignment:center;");
		//imp.setStyle("-fx-alignment:center;-fx-text-alignment:center");
	//	v.getChildren().addAll(imp,imp2);
		//v2.getChildren().addAll(tf1,passfield);
		grid.add(imp, 0, 0);
		grid.add(imp2, 0, 1);
		grid.add(tf1, 1, 0);
		grid.add(passfield, 1, 1);
		h.getChildren().addAll(grid,v,v2);
		v3.getChildren().addAll(h,val);
		//pane1.setStyle("-fx-background-color:rgb(201,225,206);-fx-padding:10px;-fx-alignment:center;");
		pane1.getChildren().addAll(v3);
		getMyDialog().setWidth(tf2.getPrefWidth()+passfield.getLayoutBounds().getWidth()+200);
		getMyDialog().setHeight(170);
	}
	
	public Button getButton(){
		return val;
	}
	public TextField getText1(){
		return tf1;
	}
	public TextField getText2(){
		return tf2;
	}
	
	public PasswordField getPassfield(){
		return this.passfield;
	}
}
