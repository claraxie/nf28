package modele;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.ObservableList;

public class Workspace implements Externalizable{
	private static final ObjectMapper mapper = new ObjectMapper();
	public ArrayList<GroupModele> groupes = new ArrayList<GroupModele>();
	public ArrayList<ContactModele> contacts = new ArrayList<ContactModele>();
	
	public Workspace() {
		
	}
	
	public Workspace(ObservableList<Group> g) {
		for(Group gg : g) {
			groupes.add(new GroupModele(gg));
		}
	}
	
	public void addGroup(Group g) {
		GroupModele gg = new GroupModele(g);
		groupes.add(gg);
	}
	
	public void addContact(Contact c) {
		contacts.add(new ContactModele(c));
		System.out.println(contacts);
	}
	
	public void rmGroup(Group g) {
		groupes.remove(new GroupModele(g));
	}
	
	public void rmContact(Contact c) {
		contacts.remove(new ContactModele(c));
	}
	
	
	public ArrayList<GroupModele> fromFile(File file) throws Exception{
		ObjectInputStream iis = new ObjectInputStream(new FileInputStream(file));
		
		String iStream;
		
		iStream = iis.readUTF();
		
		groupes = getMapper().readValue(iStream, Workspace.class).groupes;
		
		return groupes;
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//		String iStream;
//		
//		iStream = in.readUTF();
//		
//		groupes = getMapper().readValue(iStream, Workspace.class).groupes;
	}
	
	public void save(File f) throws IOException{
		System.out.println(groupes);
		System.out.println(contacts);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(this);
		oos.close();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		String oStream = getMapper().writeValueAsString(this);
		
		out.writeUTF(oStream);

	}
	
	private static ObjectMapper getMapper() {
		return mapper;
	}
	
	public void setGroups(ObservableList<Group> grps) {
		
	}
}
