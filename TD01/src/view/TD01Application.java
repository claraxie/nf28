package view;


import javafx.scene.Scene;
import javafx.stage.Stage;

public class TD01Application 
	extends javafx.application.Application {

	@Override
	public void start(Stage stage) throws 
		Exception {
		stage.setTitle("Hello");

		View panel = new View();		
		Scene scene = new Scene
				(panel,512,512);
		stage.setScene(scene);
		stage.show();
		
		Modele mod = new Modele();		
		controller cont = new controller(panel, mod);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
