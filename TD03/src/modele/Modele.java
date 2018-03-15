package modele;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class Modele {
	public Contact c1;
	private Country listPays;
	List<StringProperty> pro = new ArrayList<StringProperty>();
	public ObservableList<Group> groupes = FXCollections.observableArrayList();
	
	public Modele() {
		c1 = new Contact();
		listPays = new Country();
	}
	
	public ObservableList<String> getCountries() {
		return listPays.getCountries();
	}
	
	public boolean isContact(TreeItem<Object> cnt) {
		boolean is = false;
		for(Group g : groupes) {
			if(g.existContact(cnt))
				is = true;
		}
		return is;
	}
	
	public boolean addGroupe(String grp) {
		return groupes.add(new Group(grp));
	}
	
	public boolean addContact(String grp, Contact cnt) {
		return false;
	}
}