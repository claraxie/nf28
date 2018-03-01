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

public class Modele {
	IntegerProperty interval = new SimpleIntegerProperty();
	List<String> imageList = new ArrayList<String>();
	Timer timer = new Timer();

	public int getInterval() {
		return interval.get();
	}
	
	public void setInterval(int t) {
		interval.set(t);
	}
	
	StringProperty imageName = new SimpleStringProperty();
	public String getImageName() {
		return imageName.get();
	}
	
	public void setImageName(String n) {
		imageName.set(n);
	}
	

	
	public void setListImage() {
		File folder = new File("/images");
		File[] listFiles = folder.listFiles();
		
		for (File file : listFiles) {
			if (file.isFile()) {
				imageList.add(file.getName());
			}
		}
	}

	public void changeImage() {
		int i = imageList.indexOf(getImageName());
		if (i == imageList.size()) {
			i = -1;
		}
		setImageName(imageList.get(++i));
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void start() {
		ImageTimerTask task = new ImageTimerTask();
		getTimer().schedule(task,0,getInterval()); 		
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