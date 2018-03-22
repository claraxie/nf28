package modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {
	private StringProperty groupe =  new SimpleStringProperty(null, "", "new groupe");
	public ObservableList<Contact> contacts = FXCollections.observableArrayList();
	
	
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