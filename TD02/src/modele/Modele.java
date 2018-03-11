package modele;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Modele {
	public Contact c1;
	Country listPays;
	List<StringProperty> pro=new ArrayList<StringProperty>();
	public Modele() {
		c1 = new Contact();
		listPays = new Country();
	}
	
	public ObservableList<String> getCountries() {
		return listPays.getCountries();
	}
}