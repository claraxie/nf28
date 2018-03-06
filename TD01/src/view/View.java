package view;

import java.io.File;
import java.io.FileInputStream;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class View extends GridPane {
	private TextField interval;
	private Button btStart, btStop;
	private Slider intervalSlider;
	private Label labelInter;
	private ImageView imv;
	
	public Slider getSlider() {
		return intervalSlider;
	}
	
	public Button getStartButton() {
		return btStart;
	}
	
	public Button getStopButton() {
		return btStop;
	}
	
	public TextField getField() {
		return interval;
	}
	
	public ImageView getView() {
		return imv;
	}
	
	public View() {
		
		interval = new TextField("1955");
		btStart = new Button("Start");
		btStop = new Button("Stop");
		labelInter = new Label("Intervalle(millis)");
		intervalSlider = new Slider();
		imv = new ImageView();
		
		File image1 = new File("./images/image2.jpg");
		Image img1 = new Image(image1.toURI().toString());
		imv.setImage(img1);
		
		intervalSlider.setMin(0);
		intervalSlider.setMax(10);
		intervalSlider.setValue(2);
		intervalSlider.setShowTickLabels(true);
		intervalSlider.setShowTickMarks(true);
		intervalSlider.setMajorTickUnit(1);
		intervalSlider.setBlockIncrement(1);
		
		add(labelInter, 0, 1);
		add(interval,1, 1);
		add(btStart,0, 2);
		add(btStop,1, 2);
		add(intervalSlider,0, 3);
		add(imv, 3, 1);
	}
}
