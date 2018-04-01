package modele;

import java.time.LocalDate;

public class ContactModele extends ModelJson{
	private String prenom = "";
	private String nom = "";
	private String adresse = "";
	private String postal = "";
	private String ville = "";
	
	private String pays = "";
	private String naissance = "";
	private Boolean sexeF = false;
	private Boolean sexeM = false;
	
	public ContactModele() {
		
	}
	
	public ContactModele(Contact c) {
		prenom = c.getPrenom();
		nom = c.getNom();
		adresse = c.getAdresse();
		postal = c.getPostal();
		ville = c.getVille();
		pays = c.getPays();
		naissance = c.getNaissance();
		sexeF = c.getSexeF();
		sexeM = c.getSexeM();
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
	
	/*public Contact getContact() {
		Contact c = new Contact();
		
		c.setNaissance(naissance);
		c.setVille(ville);
		c.setPays(pays);
		c.setPrenom(prenom);
		c.setNom(nom);
		c.setAdresse(adresse);
		c.setPostal(postal);
		c.setSexeF(sexeF);
		c.setSexeM(sexeM);   	

		return c;
	}*/
}
