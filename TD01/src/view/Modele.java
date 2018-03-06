package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Modele{
	IntegerProperty interval = new SimpleIntegerProperty();
	StringProperty imageName = new SimpleStringProperty();
	BooleanProperty isStart = new SimpleBooleanProperty(false);
	List<String> imageList = new ArrayList<String>();
	Timer timer = new Timer();
	private ImageView imv;
	
	public void getImv(ImageView i) {
		imv = i;
	}
	
	
	public int getInterval() {
		return interval.get();
	}
	
	public void setInterval(int t) {
		interval.set(t);
	}
	
	public String getImageName() {
		return imageName.get();
	}
	
	public void setImageName(String n) {
		imageName.set(n);
	}
		
	public void setListImage() {
		File folder = new File("D:/java-oxygen/TD01/images");
		File[] listFiles = folder.listFiles();
		
		for (File file : listFiles) {
			if (file.isFile()) {
				imageList.add(file.getName());
			}
		}
	}

	public void changeImage() {
		int i = imageList.indexOf(getImageName());
		if (i == imageList.size()-1) {
			i = -1;
		}
		setImageName(imageList.get(++i));
		File image = new File("./images/"+getImageName());
		Image img = new Image(image.toURI().toString());
		imv.setImage(img);		
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void resume() {
		this.timer = new Timer();
		ImageTimerTask task = new ImageTimerTask();
		this.timer.schedule(task,0,getInterval());
	}
	
	public void start() {
		setListImage();
		if (isStart.get() == false) {
		ImageTimerTask task = new ImageTimerTask();
		getTimer().schedule(task,0,getInterval()); 
	} else {
		resume();
	}
		isStart.set(true);
	}
	
	public void stop() {
		getTimer().cancel();		
	}
	
	public class ImageTimerTask extends TimerTask{
		public void run() {
			changeImage();
		}
		
	}
}