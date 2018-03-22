package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;

public class Modele {
	private Country listPays;
	public ObservableList<Group> groupes = FXCollections.observableArrayList();
	public ObservableMap<String,String> errorMsgs = FXCollections.observableHashMap();
	
	StringProperty prenom = new SimpleStringProperty(null, "prenom", "");
	StringProperty nom = new SimpleStringProperty(null, "nom", "");
	StringProperty adresse = new SimpleStringProperty(null, "adresse", "");
	StringProperty postal = new SimpleStringProperty(null, "postal", "");
	StringProperty ville = new SimpleStringProperty(null, "ville", "");
	StringProperty pays = new SimpleStringProperty(null, "pays", "");
	StringProperty naissance = new SimpleStringProperty(null, "naissance", "");
	BooleanProperty sexeF = new SimpleBooleanProperty();
	BooleanProperty sexeM = new SimpleBooleanProperty();
	
	public void initialize() {
		// add listener
		adresse.addListener((observable, oldVal, newVal) -> removeFromMap(adresse.getName()));
		postal.addListener((observable, oldVal, newVal) -> removeFromMap(postal.getName()));
		ville.addListener((observable, oldVal, newVal) -> removeFromMap(ville.getName()));
		nom.addListener((observable, oldVal, newVal) -> removeFromMap(nom.getName()));
		prenom.addListener((observable, oldVal, newVal) -> removeFromMap(prenom.getName()));
	}
	
	public StringProperty getPrenom() {
		return prenom;
	}
	
	public StringProperty getNom() {
		return nom;
	}
	
	public StringProperty getAdresse() {
		return adresse;
	}
	
	public StringProperty getPostal() {
		return postal;
	}
	
	public StringProperty getVille() {
		return ville;
	}
	
	public StringProperty getPays() {
		return pays;
	}
		
	public StringProperty getNaissance() {
		return naissance;
	}
	
	public BooleanProperty getSexeF() {
		return sexeF;
	}
	
	public BooleanProperty getSexeM() {
		return sexeM;
	}
	
	public void setNaissance(StringProperty n) {
		naissance = n;
	}
	
	
	public Modele() {
		listPays = new Country();
	}
	
	public ObservableList<String> getCountries() {
		return listPays.getCountries();
	}
	
	public boolean isGroup(TreeItem<Object> grp) {
		Group c = (Group)grp.getValue();
		return groupes.contains(c);
	}
	
    public boolean validation(Contact c) {
    	boolean complete = true;
    	// Address
    			String address = adresse.getValue().toString(); 
    			String pc = postal.getValue().toString();
    			String city = ville.getValue().toString(); 
    			
    			// Person
    			String firstname = prenom.getValue().toString();
    			String lastname = nom.getValue().toString(); 
    			
    			if (address.isEmpty()) {
    				errorMsgs.put(adresse.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (pc.isEmpty()) {
    				errorMsgs.put(postal.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (city.isEmpty()) {
    				errorMsgs.put(ville.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (firstname.isEmpty()) {
    				errorMsgs.put(prenom.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (lastname.isEmpty()) {
    				errorMsgs.put(nom.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			// validate
    			if (complete) {
    				System.out.println(nom.get());
    				c.setNaissance(naissance.get());
    				c.setVille(ville.get());
    				c.setPays(pays.get());
    				c.setPrenom(prenom.get());
    				c.setNom(nom.get());
    				c.setAdresse(adresse.get());
    				c.setPostal(postal.get());
    				c.setSexeF(sexeF.getValue());
    				c.setSexeM(sexeM.getValue());   				
    			}    			
    			  			
    	return complete;
    	
    }
    
    private void removeFromMap(String key) {
		if (errorMsgs.containsKey(key))
			errorMsgs.remove(key);
	}
	
	public void setContact(Contact c) {
		adresse.set(c.getAdresse());
		naissance.set(c.getNaissance());
		nom.set(c.getNom());
		pays.set(c.getPays());
		postal.set(c.getPostal());
		prenom.set(c.getPrenom());
		sexeF.set(c.getSexeF());
		sexeM.set(c.getSexeM());
		ville.set(c.getVille());
	}
	
	public void create(TreeItem<Object> select) {
		if (select.getValue() instanceof String) {
			createGroup();
		}
		
		if (select.getValue() instanceof Group) {
			createContact((Group)select.getValue());
		}
		
		if (select.getValue() instanceof Contact) {
			createContact((Group)select.getParent().getValue());
		}
	}
	
	public void delete(TreeItem<Object> select) {
		
		if (select.getValue() instanceof String) {
			return;
		}
		
		if (select.getValue() instanceof Group) {
			groupes.remove((Group)select.getValue());

		}
		
		if (select.getValue() instanceof Contact) {
			Contact contact = (Contact)select.getValue();
			Group grp = contact.getGroup();
			grp.contacts.remove(contact);
		}
	}
	
	private void createGroup() {
		groupes.add(new Group());
	}
	
	public void createContact(Group grp) {
		grp.contacts.add(new Contact(grp));
	}
	
	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts")); 
			oos.writeObject(groupes.toArray());
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(File file) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object[] objs = (Object[])ois.readObject();
			groupes.clear();
			for (Object obj : objs) {	
				groupes.add((Group)obj);
				System.out.println(((Group)obj).contacts.size());
			}
			ois.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}