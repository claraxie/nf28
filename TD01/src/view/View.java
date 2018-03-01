package view;

import java.io.File;
import java.io.FileInputStream;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class View extends GridPane {
	private TextField interval;
	private Button btStart, btStop;
	private Slider intervalSlider;
	
	public Slider getSlider() {
		return intervalSlider;
	}
	
	public View() {
		interval = new TextField("1955");
		btStart = new Button("Start");
		btStop = new Button("Stop");
		intervalSlider = new Slider();
		ImageView imv = new ImageView();
		File image1 = new File("D:/java-oxygen/TD01/images/image2.jpg");
		Image img1 = new Image(image1.toURI().toString());
		imv.setImage(img1);
		
		intervalSlider.setMin(0);
		intervalSlider.setMax(10);
		intervalSlider.setValue(2);
		intervalSlider.setShowTickLabels(true);
		intervalSlider.setShowTickMarks(true);
		intervalSlider.setMajorTickUnit(1);
		intervalSlider.setBlockIncrement(1);
		
		
		add(interval,0, 1);
		add(btStart,0, 2);
		add(btStop,1, 2);
		add(intervalSlider,0, 3);
		add(imv, 1, 1);
	}
}
