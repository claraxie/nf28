package modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class Group {
	StringProperty groupe;
	ObservableList<Contact> contacts = FXCollections.observableArrayList();
	
	public Group(String grp) {
		groupe = new SimpleStringProperty(grp);
	}
	
	public boolean existContact(TreeItem<Object> cnt) {
		Contact c = (Contact)cnt.getValue();
		return contacts.contains(c);
	}
	
	public boolean addContact(Contact cnt) {
		return contacts.add(cnt);
	}
	
	public String getName() {
		return groupe.get();
	}
}