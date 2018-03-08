package modele;

import javafx.collections.ObservableList;

public class Modele {
	public Contact c1;
	Country listPays;
	
	public Modele() {
		c1 = new Contact();
		listPays = new Country();
	}
	
	public ObservableList<String> getCountries() {
		return listPays.getCountries();
	}
}