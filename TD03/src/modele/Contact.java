package modele;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact implements Externalizable {

	private String prenom = "";
	private String nom = "";
	private String adresse = "";
	private String postal = "";
	private String ville = "";
	
	private String pays = "";
	private String naissance = "";
	private Boolean sexeF = false;
	private Boolean sexeM = false;
			
	private Group group = null;	
	
	public Contact() {
		
	}
	
	public Contact(Group grp) {
		group = grp;
	}
	
	public void setGroup(Group grp) {
		group = grp;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public String getPostal() {
		return postal;
	}
	
	public String getVille() {
		return ville;
	}
	
	public String getPays() {
		return pays;
	}
		
	public String getNaissance() {
		return naissance;
	}
	
	public Boolean getSexeF() {
		return sexeF;
	}
	
	public Boolean getSexeM() {
		return sexeM;
	}
	
	public void setPrenom(String p) {
		this.prenom = p;
	}
	
	public void setNom(String n) {
		System.out.println("setname");
		System.out.println(n);
		this.nom = n;
	}
	
	public void setAdresse(String a) {
		this.adresse = a;
	}
	
	public void setPostal(String p) {
		this.postal = p;
	}
	
	public void setVille(String v) {
		this.ville = v;
	}
	
	public void setNaissance(String n) {
		this.naissance = n;
	}
	
	public void setPays(String n) {
		pays = n;
	}
	
	public void setSexeF(Boolean b) {
		sexeF = b;
	}
	
	public void setSexeM(Boolean b) {
		sexeM = b;
	}
	
	public String toString() {
		if (prenom.isEmpty() && nom.isEmpty())
			return "new contact";
		else
			return prenom + " " + nom;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(adresse);
		out.writeUTF(postal);
		out.writeUTF(ville);
		out.writeUTF(pays);
		out.writeUTF(prenom);
		out.writeUTF(nom);
		out.writeUTF(naissance);
		out.writeUTF(sexeF.toString());
		out.writeUTF(sexeM.toString());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub		
		adresse = in.readUTF();
		postal = in.readUTF();
		ville = in.readUTF();
		pays = in.readUTF();
		prenom = in.readUTF();
		nom = in.readUTF();
		naissance = in.readUTF();	
	}
}