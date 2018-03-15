package modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {
	StringProperty prenom = new SimpleStringProperty();
	StringProperty nom = new SimpleStringProperty();
	StringProperty adresse = new SimpleStringProperty();
	StringProperty postal = new SimpleStringProperty();
	StringProperty ville = new SimpleStringProperty();
	StringProperty pays = new SimpleStringProperty();
	StringProperty naissance = new SimpleStringProperty();
	BooleanProperty sexeF = new SimpleBooleanProperty();
	BooleanProperty sexeM = new SimpleBooleanProperty();
	
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
	
	public void setPays(String n) {
		pays.set(n);
	}
		
	public StringProperty getNaissance() {
		return naissance;
	}
	
	public void setNaissance(String n) {
		naissance.set(n);
	}
	
	public BooleanProperty getSexeF() {
		return sexeF;
	}
	
	public BooleanProperty getSexeM() {
		return sexeM;
	}
}