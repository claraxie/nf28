package view;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

public class controller {
	private View view;
	private Modele modele;
	
	public controller(View v, Modele m) {
		view = v;
		modele = m;
		
		modele.setInterval(Integer.parseInt(view.getField().getText()));
		
		view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	modele.getImv(view.getView());
		    	modele.start();
		    }
		});
		
		view.getStopButton().setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	modele.stop();
		    }
		});
		
		
		view.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
            		Number old_val, Number new_val) {
						modele.setImageName(modele.imageList.get(new_val.intValue()));
						File image = new File("./images/"+modele.getImageName());
						Image img = new Image(image.toURI().toString());
						view.getView().setImage(img);
                }
            });
		
		
		modele.imageName.addListener(  
	         (observable, oldvalue, newvalue) ->
	 			view.getSlider().setValue(modele.imageList.indexOf(newvalue))
		);;
		
		view.getField().textProperty().addListener((observable, oldValue, newValue) -> {
			modele.stop();
			modele.setInterval(Integer.parseInt(newValue));
			modele.resume();
		});		
	}
}
		


		

