package modele;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class Group implements Externalizable {
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
	
	

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(groupe.get());
		System.out.println(contacts);
		out.writeUTF(groupe.get());		
		out.writeObject(contacts.toArray());	
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		groupe.set(in.readUTF());
		Object[] objs = (Object[])in.readObject();
		contacts.clear();
		for (Object obj : objs) {	
			contacts.add((Contact)obj);
		}
	}
	
}