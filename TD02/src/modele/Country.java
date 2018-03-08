package modele;

import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {
	private ObservableList<String> pays = FXCollections.observableArrayList();
	
	public Country() {
		String[] countries = Locale.getISOCountries();
		for(String countryList : countries) {
			Locale obj = new Locale("", countryList);
			String[] p = {obj.getDisplayCountry()};
			
			for(int x = 0; x < p.length; x++) {
				pays.add(p[x]);
			}
		}
	}
	
	public ObservableList<String> getCountries() {
		return pays;
	}
}