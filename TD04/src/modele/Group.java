package modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {
	private StringProperty groupe =  new SimpleStringProperty(null, "", "new groupe");
	public ObservableList<Contact> contacts = FXCollections.observableArrayList();
	
	public Group() {
		
	}
	
	public Group(GroupModele g) {
		groupe.setValue(g.getGroupe());
		for (ContactModele c : g.getContact()) {
			contacts.add(new Contact(c));
		}
	}
	
	public StringProperty getGroupeProperty() {
		return groupe;
	}
	
	public String toString() {
		return groupe.getValue();
	}
	
	public String getName() {
		return groupe.get();
	}
}