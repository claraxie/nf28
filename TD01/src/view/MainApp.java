package view;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Modele mod = new Modele();
        	FXMLLoader viewLoader = new FXMLLoader(getClass()
                    .getResource("/view/OverView.fxml"));
        	viewLoader.setController(new MyController(mod));
        	Parent root = viewLoader.load();
               	
            primaryStage.setTitle("My Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}