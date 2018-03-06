package view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyController implements Initializable {
	   @FXML
	   private Button btStart;
	   
	   @FXML
	   private Button btStop;

	   @FXML
	   private Label labelInter;
	   
	   @FXML
	   private TextField interval;
	   
	   @FXML
	   private Slider intervalSlider;
	   
	   @FXML
	   private ImageView imv;
	   
	   private Modele modele;
	   
	   @FXML
	   private void handleStart(ActionEvent e) {
			modele.setInterval(Integer.parseInt(interval.getText()));
	    	modele.getImv(imv);
	    	modele.start();
	    }
	   
	   @FXML
	   private void handleStop(ActionEvent e) {
	    	modele.stop();
	    }
	   
	   	   
	   public MyController(Modele m) {
		   modele = m;
	   }
		
	   @Override
	   public void initialize(URL location, ResourceBundle resources) {

			intervalSlider.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov,
	           		Number old_val, Number new_val) {
							modele.setImageName(modele.imageList.get(new_val.intValue()));
							File image = new File("./images/"+modele.getImageName());
							Image img = new Image(image.toURI().toString());
							imv.setImage(img);
	               }
	           });
			
			modele.imageName.addListener(  
			         (observable, oldvalue, newvalue) ->
			 			intervalSlider.setValue(modele.imageList.indexOf(newvalue))
			);;
				
			interval.textProperty().addListener((observable, oldValue, newValue) -> {
				modele.stop();
				modele.setInterval(Integer.parseInt(newValue));
				modele.resume();
			});
	   }
}

	

		


		

