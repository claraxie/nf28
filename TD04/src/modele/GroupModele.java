package modele;

import java.util.ArrayList;

public class GroupModele extends ModelJson{
	private String groupe;
	private ArrayList<ContactModele> contacts;
	
	public GroupModele(String g, ArrayList<ContactModele> c) {
		groupe = g;
		contacts = c;
	}
	
	public GroupModele(Group g) {
		groupe = g.getName();
		for(Contact item : g.contacts) {
			System.out.println(item);
			contacts.add(new ContactModele(item));
		}
	}
	
	public void setGroupe(String g) {
		groupe = g;
	}
	
	public void setContact(ArrayList<ContactModele> c){
		contacts = c;
	}
	
	public String getGroupe() {
		return groupe;
	}
	
	public ArrayList<ContactModele> getContact(){
		return contacts;
	}
}
